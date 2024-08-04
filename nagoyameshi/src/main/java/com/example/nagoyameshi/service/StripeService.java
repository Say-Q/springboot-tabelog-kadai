package com.example.nagoyameshi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.security.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerUpdateParams;
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
				.setSuccessUrl(requestUrl.replaceAll("/user/register", "/login?registered"))
				.setCancelUrl(requestUrl)
				.putMetadata("userId", userDetailsImpl.getUser().getId().toString())
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
	public void processSessionCompleted(Event event, HttpServletRequest request) {
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresentOrElse(stripeObject -> {
			Session session = (Session) stripeObject;

			try {
				String sessionId = session.getId();
				session = Session.retrieve(sessionId,
						new SessionRetrieveParams.Builder().addExpand("subscription").build(), null);
				String userId = session.getMetadata().get("userId");

	            if (userId != null) {
	                // 顧客IDをセッションから取得
	                String customerId = session.getCustomer();

	                // 顧客メタデータを更新
	                if (customerId != null) {
	                    CustomerUpdateParams customerUpdateParams = CustomerUpdateParams.builder()
	                            .putMetadata("userId", userId)
	                            .build();
	                    Customer customer = Customer.retrieve(customerId);
	                    customer.update(customerUpdateParams);
	                }

	                // サブスクリプションIDをセッションから取得
	                String subscriptionId = session.getSubscription();
	                if (subscriptionId != null) {
	                    Subscription subscription = Subscription.retrieve(subscriptionId);
	                    Map<String, String> metadata = subscription.getMetadata();
	                    metadata.put("userId", userId);
	                    Map<String, Object> updateParams = new HashMap<>();
	                    updateParams.put("metadata", metadata);
	                    subscription.update(updateParams);
	                }

	                userService.upgradeRole(Integer.parseInt(userId));
	                //ユーザーをログアウトする
	                SecurityContextHolder.clearContext();
	                request.getSession().invalidate(); // セッションを無効化
	                System.out.println(SecurityContextHolder.getContext().getAuthentication());
	                
	            } else {
	                System.out.println("ユーザーIDをメタデータから取得できませんでした。");
	            }

	            System.out.println("Stripe API Version" + event.getApiVersion());
	            System.out.println("stripe-java Version" + Stripe.VERSION);

	        } catch (StripeException e) {
	            e.printStackTrace();
	        }
	    }, () -> {
	        System.out.println("有料会員登録が失敗しました。");
	        System.out.println("Stripe API Version" + event.getApiVersion());
	        System.out.println("stripe-java Version" + Stripe.VERSION);
	    });
	}
	
	public void cancelSubscription(String subscriptionId) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		
		Subscription subscription = Subscription.retrieve(subscriptionId);
		subscription.cancel();
	}
	
}
