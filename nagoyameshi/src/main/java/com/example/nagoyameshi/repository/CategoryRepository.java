package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.nagoyameshi.entity.Categories;


public interface CategoryRepository extends JpaRepository<Categories, Integer>{
	public Categories findByid(Integer id);

	@Query("SELECT coalesce(max(c.id),0) FROM Categories c")
	int findMaxId();
}