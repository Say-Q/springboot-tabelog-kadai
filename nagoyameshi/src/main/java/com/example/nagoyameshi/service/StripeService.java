package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public StripeService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	// セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(UserDetailsImpl userDetailsImpl, HttpServletRequest httpServletRequest)
			throws StripeException {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(SessionCreateParams.LineItem.builder()
						.setPriceData(SessionCreateParams.LineItem.PriceData.builder()
								.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName("NAGOYAMESHI有料会員").build())
								.setUnitAmount(300L)
								.setCurrency("jpy").build())
						.setQuantity(1L).build())
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl(
						requestUrl.replaceAll("/api/checkout-session", "")
								+ "/api/saccess?session_id={CHECKOUT_SESSION_ID}")
				.setCancelUrl(requestUrl.replace("/api/checkout-session", ""))
				.setPaymentIntentData(
						SessionCreateParams.PaymentIntentData.builder()
								.putMetadata("userId", userDetailsImpl.getUser().getId().toString())
								.putMetadata("amount", "300".toString())
								.build())
				.build();
		Session session = Session.create(params);
		return session.getId();
	}

	@Transactional
	public void updateRoleAfterPayment(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		Role role = roleRepository.findByName("ROLE_PAYMEMBER");
		user.setRole(role);
		userRepository.save(user);
	}
}
