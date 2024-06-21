package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.repository.CompanyRepository;
import com.example.nagoyameshi.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {
	private final CompanyRepository companyRepository;
	private final CompanyService companyService;

	public CompanyController(CompanyRepository companyRepository, CompanyService companyService) {
		this.companyRepository = companyRepository;
		this.companyService = companyService;
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

	@GetMapping("/edit")
	public String edit(Model model) {
		Company company = companyRepository.findById(1).orElse(null);

		//フォームクラスをインスタンス化する
		CompanyEditForm companyEditForm = new CompanyEditForm(company.getId(), company.getName(),
				company.getPostalCode(), company.getLocation(), company.getRepresentative(), company.getEstablishment(),
				company.getCapital(), company.getContent());

		//生成したインスタンスをビューに渡す
		model.addAttribute("companyEditForm", companyEditForm);

		model.addAttribute("company", company);

		return "admin/company/edit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute @Validated CompanyEditForm companyEditForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {

			return "admin/company/edit";

		}

		companyService.update(companyEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "会社情報を編集しました。");
		return "redirect:/admin";
	}

}
