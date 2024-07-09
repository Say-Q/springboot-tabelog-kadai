package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Categories;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional
	public void create(CategoryRegisterForm categoryRegisterForm) {
//		List<Categories> categoryid = categoryRepository.findAll();
		int nextId = categoryRepository.findMaxId() + 1;
		
		Categories category = new Categories();
		category.setId(nextId);
		category.setCategoryName(categoryRegisterForm.getName());

		categoryRepository.save(category);
	}
	
	@Transactional
	public void update(CategoryEditForm categoryEditForm) {
		Categories category = categoryRepository.getReferenceById(categoryEditForm.getId());
		
		category.setCategoryName(categoryEditForm.getName());
		
		categoryRepository.save(category);
		
	}
}
