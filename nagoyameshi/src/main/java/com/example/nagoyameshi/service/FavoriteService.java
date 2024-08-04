package com.example.nagoyameshi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;

import jakarta.transaction.Transactional;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;

	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	//お気に入り追加
	@Transactional
	public void addFav(Shop shop, User user) {
		Favorite favorite = new Favorite();
		favorite.setShop(shop);
		favorite.setUser(user);
		favoriteRepository.save(favorite);
	}

	//お気に入り削除
	@Transactional
	public void deleteFav(Shop shop, User user) {
		Optional<Favorite> favoriteOpt = favoriteRepository.findByShopAndUser(shop, user);
			favoriteOpt.ifPresent(favoriteRepository::delete);
	}

	@Transactional
	public void allDeleteFav(User user) {
		favoriteRepository.deleteAllByUser(user);
	}
}
