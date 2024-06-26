package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.favorite;

public interface FavoriteRepository extends JpaRepository<favorite, Integer>{
	public Page<favorite> findByShopIdAndUserId(Integer shopid, Integer userid, Pageable pageable);
}
