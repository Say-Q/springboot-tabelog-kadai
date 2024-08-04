package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Categories;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;

	public FavoriteController(ShopRepository shopRepository, CategoryRepository categoryRepository,
			FavoriteRepository favoriteRepository, FavoriteService favoriteService) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
	}

	@GetMapping
	public String favorite(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
			Model model) {

		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage;
		List<Categories> category = categoryRepository.findAll();

		boolean isOrder = Objects.nonNull(order) && order.equals("createdAtAsc");

		if (isOrder) {
			favoritePage = favoriteRepository.findByUserOrderByCreatedAtAsc(user, pageable);
		} else {
			favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		}
		model.addAttribute("favoritePage", favoritePage);
		model.addAttribute("categories", category);
		model.addAttribute("order", order);

		return "user/favorite";
	}

	//お気に入り登録
	@PostMapping("/add")
	public String addFavorite(@RequestParam("shopId") Integer shopId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		Optional<Shop> shopOpt = shopRepository.findById(shopId);
		shopOpt.ifPresent(shop -> favoriteService.addFav(shop, user));

		return "redirect:/shops/" + shopId;
	}

	//お気に入り削除
	@PostMapping("/delete")
	public String deleteFav(@RequestParam("shopId") Integer shopId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		Optional<Shop> shopOpt = shopRepository.findById(shopId);
		shopOpt.ifPresent(shop -> favoriteService.deleteFav(shop, user));

		return "redirect:/shops/" + shopId;
	}
}
