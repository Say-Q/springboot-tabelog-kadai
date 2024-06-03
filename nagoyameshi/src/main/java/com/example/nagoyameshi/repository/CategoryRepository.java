package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Categories;


public interface CategoryRepository extends JpaRepository<Categories, Integer>{
	public Categories findByid(Integer id);
}