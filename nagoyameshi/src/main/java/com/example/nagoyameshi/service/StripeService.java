package com.example.nagoyameshi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.security.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	private final UserService userService;

	@Autowired
	public StripeService(UserService userService) {
		this.userService = userService;
	}

	// セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(UserDetailsImpl userDetailsImpl, HttpServletRequest httpServletRequest) {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUser().getId().toString();

		//定期料金オブジェクトの定義
		SessionCreateParams.LineItem.PriceData.Recurring recurring = SessionCreateParams.LineItem.PriceData.Recurring
				.builder()
				.setInterval(SessionCreateParams.LineItem.PriceData.Recurring.Interval.MONTH).build();

		//価格データを価格の繰り返し情報で設定
		SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
				.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName("NAGOYAMESHI有料会員").build())
				.setUnitAmount(300L)
				.setCurrency("jpy")
				.setRecurring(recurring) //ここで定期的な料金設定
				.build();

		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				.setPriceData(priceData)
				.setQuantity(1L)
				.build();

		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(lineItem)
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl(requestUrl.replaceAll("/register", ""))
				.setCancelUrl(requestUrl.replace("/user/register", ""))
				.setClientReferenceId(userId)
				.build();

		try {

			Session session = Session.create(params);
			return session.getId();

		} catch (StripeException e) {

			e.printStackTrace();
			return "";
		}
	}

	// セッションから利用者情報を取得し、UserServiceクラスを介してRoleを変更する
	@Transactional
	public void processSessionCompleted(Event event) {
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresentOrElse(stripeObject -> {
			Session session = (Session) stripeObject;
			SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();

			try {

				session = Session.retrieve(session.getId(), params, null);
				//ユーザーIDを取得
				Integer id = Integer.parseInt(session.getClientReferenceId());
				userService.roleUpdate(id);

			} catch (StripeException e) {

				e.printStackTrace();

			}

			System.out.println("有料会員登録が完了しました。");
			System.out.println("Stripe API Version" + event.getApiVersion());
			System.out.println("stripe-java Version" + Stripe.VERSION);

		}, () -> {

			System.out.println("有料会員登録が失敗しました。");
			System.out.println("Stripe API Version" + event.getApiVersion());
			System.out.println("stripe-java Version" + Stripe.VERSION);

		});

	}
}
