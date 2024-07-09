package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaidController {
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
	public String showRegistrerForm() {

		return "user/register"; // entry.htmlを返す
	}

	@PostMapping("/user/register")
	public String registerUser(/*ユーザー情報の取得*/) {
		// ユーザー登録処理
		return "redirect:/success"; // 登録成功ページへリダイレクト
	}

}
