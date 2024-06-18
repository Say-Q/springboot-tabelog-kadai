package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.repository.CompanyRepository;


@Controller
@RequestMapping("/company")
public class CompanyController {
	private final CompanyRepository companyRepository;
	
	public CompanyController(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@GetMapping("/terms")
	public String terms() {

		return "admin/company/terms";
	}

	@GetMapping("/show")
	public String show(Model model) {
		Company company = companyRepository.findById(1).orElse(null);
		
		model.addAttribute("company", company);
		
		return "admin/company/show";
	}
	
	@PostMapping("/company/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		
		return "admin/company/edit";
	}
	
}
