package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PaidController {
	private final StripeService stripeService;

	public PaidController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	@GetMapping("/company/paidterms")
	public String paidterms() {

		return "admin/company/paidterms";
	}

	@GetMapping("/paid-terms")
	public String showPaidTerms() {

		return "paid-terms";
	}

	@GetMapping("/company/paidterms-body")
	@ResponseBody
	public String getPaidTermsBody() throws IOException {

		Resource resource = new ClassPathResource("templates/admin/company/paidterms.html");

		String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

		// 特定の<div>要素を抽出
		String startTag = "<div class=\"terms-content\">";
		String endTag = "</div>";
		int startIndex = content.indexOf(startTag);
		int endIndex = content.indexOf(endTag, startIndex) + endTag.length();

		if (startIndex != -1 && endIndex != -1) {

			return content.substring(startIndex, endIndex);

		} else {

			return "内容が見つかりませんでした";
		}
	}

	@GetMapping("/user/register")
	public String payment(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			HttpServletRequest httpServletRequest, Model model) {
		User user = userDetailsImpl.getUser();
		String sessionId = stripeService.createStripeSession(userDetailsImpl, httpServletRequest);
		
		model.addAttribute("user", user);
		model.addAttribute("sessionId", sessionId);

		return "user/register"; // entry.htmlを返す
	}

}
