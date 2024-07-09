package com.example.nagoyameshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StripeWebhookController {
	@Autowired
	private StripeService stripeService;
//	@Value("${stripe.api-key}")
//	private String stripeApiKey;

	@PostMapping("/api/checkout-session")
	public ResponseEntity<String> createStripeSession(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			HttpServletRequest request) {
		try {
			String sessionId = stripeService.createStripeSession(userDetailsImpl, request);
			return ResponseEntity.ok(sessionId);

		} catch (StripeException e) {
			return ResponseEntity.status(500).body("エラーが発生しました：" + e.getMessage());
		}
	}

	@GetMapping("/api/success")
	public ResponseEntity<String> handlecheckoutSuccess(@RequestParam("session_id") String sessionId) {
//		Stripe.apiKey = stripeApiKey;
		try {
			Session session = Session.retrieve(sessionId);

			Integer userId = Integer.parseInt(session.getMetadata().get("userId"));
			stripeService.updateRoleAfterPayment(userId);
			return ResponseEntity.ok("決済成功");
		} catch (StripeException | NumberFormatException e) {
			return ResponseEntity.status(500).body("エラーが発生しました: " + e.getMessage());
		}
	}

}
