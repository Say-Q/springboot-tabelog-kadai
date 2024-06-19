package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.repository.CompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class CompanyService {
	private final CompanyRepository companyRepository;
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Transactional
	public void update(CompanyEditForm companyEditForm) {
		Company company = companyRepository.getReferenceById(companyEditForm.getId());
		
		company.setName(companyEditForm.getName());
		company.setPostalCode(companyEditForm.getPostalCode());
		company.setLocation(companyEditForm.getLocation());
		company.setRepresentative(companyEditForm.getRepresentative());
		company.setEstablishment(companyEditForm.getEstablishment());
		company.setCapital(companyEditForm.getCapital());
		company.setContent(companyEditForm.getContent());
		
		companyRepository.save(company);
		
	}
}
