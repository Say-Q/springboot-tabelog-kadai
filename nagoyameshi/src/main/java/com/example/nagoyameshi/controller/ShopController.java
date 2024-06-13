package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Categories;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
@RequestMapping("/shops")
public class ShopController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;

	public ShopController(ShopRepository shopRepository, CategoryRepository categoryRepository) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "categoriesid", required = false) Integer categoriesid,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {

		Page<Shop> shopPage;

		//		int word1_cateid1_price1 = (Objects.nonNull(keyword) && !keyword.isEmpty() && Objects.nonNull(categoriesid) && Objects.nonNull(price)) ? 111 : 0;
		//		int word1_cateid1 = (Objects.nonNull(keyword) && !keyword.isEmpty() && Objects.nonNull(categoriesid)) ? 110 : 0;
		//		int word1_price1 = (Objects.nonNull(keyword) && !keyword.isEmpty() && Objects.nonNull(price)) ? 101 : 0;
		//		int cateid1_price1 = (Objects.nonNull(categoriesid) && Objects.nonNull(price)) ? 011 : 0;
		//		int word1 = (Objects.nonNull(keyword) && !keyword.isEmpty()) ? 100 : 0;
		//		int cateid1 = (Objects.nonNull(categoriesid)) ? 010 : 0;
		//		int price1 = (Objects.nonNull(price)) ? 001 : 0;
		//		int order1 = (Objects.nonNull(order) && order.equals("priceDesc")) ? 2 : 1;

		boolean keywordck = Objects.nonNull(keyword) && !keyword.isEmpty();
		boolean categoriesidck = Objects.nonNull(categoriesid);
		boolean priceck = Objects.nonNull(price);
		boolean orderck = Objects.nonNull(order) && order.equals("priceDesc");

		if (keywordck && categoriesidck && priceck) {
			if (orderck && order.equals("priceDesc")) {
				shopPage = shopRepository.findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(
						"%" + keyword + "%", categoriesid, price, pageable);
			} else {
				shopPage = shopRepository.findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(
						"%" + keyword + "%", categoriesid, price, pageable);
			}
		} else if (keywordck && categoriesidck) {

			if (orderck) {
				shopPage = shopRepository.findByNameLikeAndCategoriesIdOrderByPriceDesc("%" + keyword + "%",
						categoriesid, pageable);
			} else {
				shopPage = shopRepository.findByNameLikeAndCategoriesIdOrderByPriceAsc("%" + keyword + "%",
						categoriesid, pageable);
			}

		} else if (keywordck && priceck) {

			if (orderck) {
				shopPage = shopRepository.findByNameLikeAndPriceLessThanEqualOrderByPriceDesc("%" + keyword + "%",
						price, pageable);
			} else {
				shopPage = shopRepository.findByNameLikeAndPriceLessThanEqualOrderByPriceAsc("%" + keyword + "%", price,
						pageable);
			}

		} else if (categoriesidck && priceck) {

			if (orderck) {
				shopPage = shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(categoriesid, price,
						pageable);
			} else {
				shopPage = shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(categoriesid, price,
						pageable);
			}

		} else if (keywordck) {

			if (orderck) {
				shopPage = shopRepository.findByNameLikeOrderByPriceDesc("%" + keyword + "%", pageable);
			} else {
				shopPage = shopRepository.findByNameLikeOrderByPriceAsc("%" + keyword + "%", pageable);
			}

		} else if (categoriesidck) {

			if (orderck) {
				shopPage = shopRepository.findByCategoriesIdOrderByPriceDesc(categoriesid, pageable);
			} else {
				shopPage = shopRepository.findByCategoriesIdOrderByPriceAsc(categoriesid, pageable);
			}

		} else if (priceck) {

			if (orderck) {
				shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceDesc(price, pageable);
			} else {
				shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
			}

		} else {

			if (orderck) {
				shopPage = shopRepository.findAllByOrderByPriceDesc(pageable);
			} else {
				shopPage = shopRepository.findAllByOrderByPriceAsc(pageable);
			}

		}

		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoriesid", categoriesid);
		model.addAttribute("price", price);
		model.addAttribute("order", order);

		return "shops/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		List<Categories> category = categoryRepository.findAll();

		model.addAttribute("shop", shop);
		model.addAttribute("category", category);

		return "shops/show";
	}

}
