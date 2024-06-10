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
			@RequestParam(name = "categoryid", required = false) Integer categoryid,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {

		Page<Shop> shopPage;
		
		boolean keywordck = keyword != null && !keyword.isEmpty();
		boolean categoryidck = categoryid != null;
		boolean priceck = price != null;
		
		if (keyword != null && !keyword.isEmpty() && categoryid != null && Objects.nonNull(price)) {
			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByNameAndCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(
						"%" + keyword + "%", categoryid, price, pageable);
			} else {
				shopPage = shopRepository.findByNameAndCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(
						"% " + keyword + "%", categoryid, price, pageable);
			}

		} else if (keyword != null && !keyword.isEmpty() && categoryid != null) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByNameAndCategoriesIdOrderByPriceDesc("%" + keyword + "%%", categoryid, pageable);
			} else {
				shopPage = shopRepository.findByNameAndCategoriesIdOrderByPriceAsc("%" + keyword + "%", categoryid, pageable);
			}

		} else if (keyword != null && keyword.isEmpty() && price != null) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByNameAndPriceLessThanEqualOrderByPriceDesc("%" + keyword + "%",
						price, pageable);
			} else {
				shopPage = shopRepository.findByNameAndPriceLessThanEqualOrderByPriceAsc("%" + keyword + "%", price,
						pageable);
			}

		} else if (categoryid != null && price != null) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(categoryid, price,
						pageable);
			} else {
				shopPage = shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(categoryid, price,
						pageable);
			}

		} else if (keywordck) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByNameLikeOrderByPriceDesc("%" + keyword + "%", pageable);
			} else {
				shopPage = shopRepository.findByNameLikeOrderByPriceAsc("%" + keyword + "%", pageable);
			}

		} else if (categoryid != null) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByCategoriesIdOrderByPriceDesc(categoryid, pageable);
			} else {
				shopPage = shopRepository.findByCategoriesIdOrderByPriceAsc(categoryid, pageable);
			}

		} else if (price != null) {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceDesc(price, pageable);
			} else {
				shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
			}

		} else {

			if (order != null && order.equals("priceDesc")) {
				shopPage = shopRepository.findAllByOrderByPriceDesc(pageable);
			} else {
				shopPage = shopRepository.findAllByOrderByPriceAsc(pageable);
			}

		}

		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryid", categoryid);
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
