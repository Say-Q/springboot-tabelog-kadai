package com.example.nagoyameshi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.nagoyameshi.security.SessionManagement;
import com.example.nagoyameshi.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StripeWebhookController {
	private final StripeService stripeService;

	@Value("${stripe.api-key}")
	private String stripeApiKey;

	@Value("${stripe.webhook-secret}")
	private String webhookSecret;

	public StripeWebhookController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	@PostMapping("/stripe/webhook")
	public ResponseEntity<String> webhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		try {

			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		} catch (SignatureVerificationException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		}

		if ("checkout.session.completed".equals(event.getType())) {
			stripeService.processSessionCompleted(event);

			// Spring Security コンテキストに新しいロールを反映
			SessionManagement.updateSessionRole(request);

		}

		return new ResponseEntity<>("Success", HttpStatus.OK);

	}

}
