package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.Event;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodListParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StripeWebhookController {
	private final StripeService stripeService;
	private final UserService userService;
	private final FavoriteService favoriteService;

	@Value("${stripe.api-key}")
	private String stripeApiKey;

	@Value("${stripe.webhook-secret}")
	private String webhookSecret;

	public StripeWebhookController(StripeService stripeService, UserService userService,
			FavoriteService favoriteService) {
		this.stripeService = stripeService;
		this.userService = userService;
		this.favoriteService = favoriteService;
	}

	@PostMapping("/stripe/webhook")
	public ResponseEntity<String> webhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader, HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		try {

			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		} catch (SignatureVerificationException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		}

		if ("checkout.session.completed".equals(event.getType())) {
			stripeService.processSessionCompleted(event, request);

			// 302 (FOUND) ステータスコードでリダイレクトを指定
			response.setStatus(HttpServletResponse.SC_FOUND);
			response.setHeader("Location", "/login?registered");

		}

		return new ResponseEntity<>("Success", HttpStatus.OK);

	}

	@GetMapping("/getCurrentCard")
	@ResponseBody
	public Map<String, String> getCurrentCard(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Map<String, String> response = new HashMap<>();
		try {
			Stripe.apiKey = stripeApiKey;
			String id = userDetailsImpl.getUser().getId().toString();

			// 顧客の検索
			CustomerSearchParams params = CustomerSearchParams.builder()
					.setQuery("metadata['userId']:'" + id + "'")
					.build();
			CustomerSearchResult customers = Customer.search(params);

			if (!customers.getData().isEmpty()) {
				Customer customer = customers.getData().get(0);

				// デフォルトの支払い方法を取得
				PaymentMethodCollection paymentMethods = PaymentMethod.list(PaymentMethodListParams.builder()
						.setCustomer(customer.getId())
						.setType(PaymentMethodListParams.Type.CARD)
						.build());

				if (!paymentMethods.getData().isEmpty()) {
					PaymentMethod paymentMethod = paymentMethods.getData().get(0);
					PaymentMethod.Card card = paymentMethod.getCard();

					response.put("cardLast4", card.getLast4());
					response.put("expMonth", String.valueOf(card.getExpMonth()));
					response.put("expYear", String.valueOf(card.getExpYear()));
				}
			}
		} catch (StripeException e) {
			response.put("error", e.getMessage());
		}

		return response;
	}

	@PostMapping("/stripeUpdate")
	@ResponseBody
	public Map<String, Object> stripeUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@RequestBody Map<String, String> request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Stripe.apiKey = stripeApiKey;
			String id = userDetailsImpl.getUser().getId().toString();

			System.out.println("Received request: " + request);
			// 顧客の検索
			CustomerSearchParams params = CustomerSearchParams.builder()
					.setQuery("metadata['userId']:'" + id + "'")
					.build();
			CustomerSearchResult customers = Customer.search(params);

			if (customers.getData().isEmpty()) {
				response.put("success", false);
				response.put("error", "Customer not found");
				return response;
			}

			// 顧客が見つかった場合、最初の顧客を取得
			Customer customer = customers.getData().get(0);

			// 新しい支払い方法を顧客にアタッチ
			String paymentMethodId = request.get("paymentMethodId");
			PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

			PaymentMethodAttachParams attachParams = PaymentMethodAttachParams.builder()
					.setCustomer(customer.getId())
					.build();
			paymentMethod.attach(attachParams);

			// 新しい支払い方法以外の既存の支払い方法を削除
			PaymentMethodCollection paymentMethods = PaymentMethod.list(PaymentMethodListParams.builder()
					.setCustomer(customer.getId())
					.setType(PaymentMethodListParams.Type.CARD)
					.build());

			for (PaymentMethod pm : paymentMethods.getData()) {
				if (!pm.getId().equals(paymentMethodId)) {
					pm.detach(); // 支払い方法を削除
				}
			}
			// 顧客のデフォルトの支払い方法を更新
			Map<String, Object> paramsUpdate = new HashMap<>();
			paramsUpdate.put("invoice_settings", Collections.singletonMap("default_payment_method", paymentMethodId));

			// 更新処理
			Customer updatedCustomer = Customer.retrieve(customer.getId()).update(paramsUpdate);

			response.put("success", true);
			response.put("customer", updatedCustomer.getId());

		} catch (StripeException e) {
			response.put("success", false);
			response.put("error", e.getMessage());
		}

		return response;
	}

	@PostMapping("/cancel-subscription")
	@ResponseBody
	public Map<String, Object> cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Map<String, Object> response = new HashMap<>();
		try {
			Stripe.apiKey = stripeApiKey;
			String id = userDetailsImpl.getUser().getId().toString();
			User user = userDetailsImpl.getUser();
			CustomerSearchParams params = CustomerSearchParams.builder()
					.setQuery("metadata['userId']:'" + id + "'")
					.build();
			CustomerSearchResult customers = Customer.search(params);

			if (!customers.getData().isEmpty()) {
				Customer customer = customers.getData().get(0);
				customer.delete();
				response.put("success", true);
				userService.downgradeRole(Integer.valueOf(id));
				favoriteService.allDeleteFav(user);

			} else {
				response.put("success", false);
				response.put("error", "Customer not found");
			}
		} catch (StripeException e) {
			response.put("success", false);
			response.put("error", e.getMessage());
		}

		return response;
	}
}
