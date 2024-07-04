package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaidController {
	@GetMapping("/company/paidterms")
	public String paidterms() {

		return "admin/company/paidterms";
	}

	@GetMapping("/user/entry")
	public String showRegistrationForm() {
		
		return "user/entry"; // entry.htmlを返す
	}
	
	@PostMapping("/user/entry")
	public String registerUser(/*ユーザー情報の取得*/) {
		// ユーザー登録処理
		return "redirect:/success"; // 登録成功ページへリダイレクト
	}


}
