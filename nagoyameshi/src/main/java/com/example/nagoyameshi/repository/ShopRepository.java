package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	public Page<Shop> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Shop> findByNameLikeOrderByPriceAsc(String namekeyword, Pageable pageable);
	public Page<Shop> findByNameLikeOrderByPriceDesc(String namekeyword, Pageable pageable);
	
	public Page<Shop> findByCategoriesIdOrderByPriceAsc(Integer categoryid, Pageable pageable);
	public Page<Shop> findByCategoriesIdOrderByPriceDesc(Integer categoryid, Pageable pageable);
	
	public Page<Shop> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);
	public Page<Shop> findByPriceLessThanEqualOrderByPriceDesc(Integer price, Pageable pageable);
	
	public Page<Shop> findByNameAndCategoriesIdOrderByPriceAsc(String nameKeyword, Integer categoryid, Pageable pageable);
	public Page<Shop> findByNameAndCategoriesIdOrderByPriceDesc(String nameKeyword, Integer categoryid, Pageable pageable);
	
	public Page<Shop> findByNameAndPriceLessThanEqualOrderByPriceAsc(String nameKeyword, Integer price, Pageable pageable);
	public Page<Shop> findByNameAndPriceLessThanEqualOrderByPriceDesc(String nameKeyword, Integer price, Pageable pageable);
	
	public Page<Shop> findByCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(Integer categoryid, Integer price, Pageable pageable);
	public Page<Shop> findByCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(Integer categoryid, Integer price, Pageable pageable);
	
	public Page<Shop> findByNameAndCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(String nameKeyword, Integer categoryid, Integer price, Pageable pageable);
	public Page<Shop> findByNameAndCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(String nameKeyword, Integer categoryid, Integer price, Pageable pageable);
	
	public Page<Shop> findAllByOrderByPriceAsc(Pageable pageable);
	public Page<Shop> findAllByOrderByPriceDesc(Pageable pageable);
	
	public List<Shop> findTop10ByOrderByPriceAsc();
}
