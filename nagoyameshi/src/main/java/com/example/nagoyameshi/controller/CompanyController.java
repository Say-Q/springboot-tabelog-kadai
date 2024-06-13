package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@GetMapping("/terms")
	public String terms() {

		return "admin/company/terms";
	}

	@GetMapping("/companyshow")
	public String companyshow() {
		return "admin/company/companyshow";
	}
}
