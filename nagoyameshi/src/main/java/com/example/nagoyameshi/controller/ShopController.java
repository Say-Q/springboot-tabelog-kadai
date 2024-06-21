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
		List<Categories> category = categoryRepository.findAll();

		shopPage = searchshop(keyword, categoriesid, price, order, pageable);

		model.addAttribute("shopPage", shopPage);
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoriesid", categoriesid);
		model.addAttribute("price", price);
		model.addAttribute("order", order);

		return "shops/index";
	}

	public Page<Shop> searchshop(String keyword, Integer categoriesid, Integer price, String order, Pageable pageable) {
		Page<Shop> shopPage;

		boolean isKeyword = Objects.nonNull(keyword) && !keyword.isEmpty();
		boolean isCategoriesid = Objects.nonNull(categoriesid);
		boolean isPrice = Objects.nonNull(price);
		boolean isOrder = Objects.nonNull(order) && order.equals("priceDesc");
		//seitchのkeyとして使うために、三項演算子でboolean型をint型に変更。
		int keywordid = (isKeyword == true) ? 1 : 0;
		int categoryid = (isCategoriesid == true) ? 2 : 0;
		int priceid = (isPrice == true) ? 4 : 0;

		int key = keywordid + categoryid + priceid;

		switch (key) {
		case 1:
			return shopPage = (isOrder == true) ? shopRepository.findByNameLikeOrderByPriceDesc("%" + keyword + "%", pageable)
					: shopRepository.findByNameLikeOrderByPriceAsc("%" + keyword + "%", pageable);
		case 2:
			return shopPage = (isOrder == true)
					? shopRepository.findByCategoriesIdOrderByPriceDesc(categoriesid, pageable)
					: shopRepository.findByCategoriesIdOrderByPriceAsc(categoriesid, pageable);
		case 3:
			return shopPage = (isOrder == true)
					? shopRepository.findByNameLikeAndCategoriesIdOrderByPriceDesc("%" + keyword + "%", categoriesid, pageable)
					: shopRepository.findByNameLikeAndCategoriesIdOrderByPriceAsc("%" + keyword + "%", categoriesid, pageable);
		case 4:
			return shopPage = (isOrder == true)
					? shopRepository.findByPriceLessThanEqualOrderByPriceDesc(price, pageable)
					: shopRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
		case 5:
			return shopPage = (isOrder == true)
					? shopRepository.findByNameLikeAndPriceLessThanEqualOrderByPriceDesc("%" + keyword + "%", price, pageable)
					: shopRepository.findByNameLikeAndPriceLessThanEqualOrderByPriceAsc("%" + keyword +"%", price, pageable);
		case 6:
			return shopPage = (isOrder == true)
					? shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceDesc(categoriesid, price,
							pageable)
					: shopRepository.findByCategoriesIdAndPriceLessThanEqualOrderByPriceAsc(categoriesid, price,
							pageable);
		case 7:
			return shopPage = (isOrder == true)
					? shopRepository.findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceDesc("%" + keyword + "%",
							categoriesid, price, pageable)
					: shopRepository.findByNameLikeAndCategoriesIdAndPriceLessThanEqualOrderByPriceAsc("%" + keyword + "%",
							categoriesid, price, pageable);
		default:
			return shopPage = (isOrder == true) ? shopRepository.findAllByOrderByPriceDesc(pageable)
					: shopRepository.findAllByOrderByPriceAsc(pageable);
		}

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
