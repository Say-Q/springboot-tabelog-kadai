package com.example.nagoyameshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.repository.CompanyRepository;


@Controller
public class AdminController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@GetMapping("/admin")
	public String index(Model model) {
		//会社情報を取得
		Company company = companyRepository.findById(1).orElse(new Company());
		
		
		model.addAttribute("company", company);

		return "/admin/index";
	}
	
}
