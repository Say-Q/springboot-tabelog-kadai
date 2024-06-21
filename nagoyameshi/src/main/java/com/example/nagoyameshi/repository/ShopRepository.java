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
	
	public Page<Shop> findByCategoriesIdOrderByPriceAsc(Integer categoriesid, Pageable pageable);
	public Page<Shop> findByCategoriesIdOrderByPriceDesc(Integer categoriesid, Pageable pageable);
	
	public Page<Shop> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);
	public Page<Shop> findByPriceLessThanEqualOrderByPriceDesc(Integer price, Pageable pageable);
	
	public Page<Shop> findByNameLikeAndCategoriesIdOrderByPriceAsc(String nameKeyword, Integer categoriesid, Pageable pageable);
	public Page<Shop> findByNameLikeAndCategoriesIdOrderByPriceDesc(String nameKeyword, Integer categoryiesid, Pageable pageable);
	
	public Page<Shop> findByNameLikeAndPriceLessThanEqualOrderByPriceAsc(String nameKeyword, Integer price, Pageable pageable);
	public Page<Shop> findByNameLikeAndPriceLessThanEqualOrderByPriceDesc(String nameKeyword, Integer price, Pageable pageable);
	
	public Page<Shop> findByCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(Integer categoriesid, Integer price, Pageable pageable);
	public Page<Shop> findByCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(Integer categoriesid, Integer price, Pageable pageable);
	
	public Page<Shop> findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(String nameKeyword, Integer categoriesid, Integer price, Pageable pageable);
	public Page<Shop> findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(String nameKeyword, Integer categoriesid, Integer price, Pageable pageable);
	
	public Page<Shop> findAllByOrderByPriceAsc(Pageable pageable);
	public Page<Shop> findAllByOrderByPriceDesc(Pageable pageable);
	
	//店舗登録一覧で使用
	public Page<Shop> findByNameLikeOrderByUpdatedAtAsc(String namekeyword, Pageable pageable);
	public Page<Shop> findByNameLikeOrderByUpdatedAtDesc(String namekeyword, Pageable pageable);
	
	public Page<Shop> findAllByOrderByUpdatedAtAsc(Pageable pageable);
	public Page<Shop> findAllByOrderByUpdatedAtDesc(Pageable pageable);
	
	//トップページで使用
	public List<Shop> findTop10ByOrderByPriceAsc();
}
