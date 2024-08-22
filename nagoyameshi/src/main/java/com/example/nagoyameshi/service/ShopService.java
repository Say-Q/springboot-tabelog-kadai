package com.example.nagoyameshi.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopEditForm;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.ShopRepository;
import com.opencsv.CSVWriter;

@Service
public class ShopService {
	private final ShopRepository shopRepository;

	public ShopService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	@Transactional
	public void create(ShopRegisterForm shopRegisterForm) {
		Shop shop = new Shop();

		MultipartFile imageFile = shopRegisterForm.getImageFile();

		if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			shop.setImageName(hashedImageName);
		}
		//コンマ区切りの曜日を取得
		String[] holidays = shopRegisterForm.getRegularHoliday().split(",");
		//日本語曜日に変換し、再度コンマ区切りのStringにまとめる
		String regularHoliday = Arrays.stream(holidays)
				.map(this::convertToJapaneseDay)
				.collect(Collectors.joining(","));

		shop.setName(shopRegisterForm.getName());
		shop.setCategoriesId(shopRegisterForm.getCategoriesId());
		shop.setDescription(shopRegisterForm.getDescription());
		shop.setPostalCode(shopRegisterForm.getPostalCode());
		shop.setAddress(shopRegisterForm.getAddress());
		shop.setPhoneNumber(shopRegisterForm.getPhoneNumber());
		shop.setOpenTime(shopRegisterForm.getOpenTime());
		shop.setCloseTime(shopRegisterForm.getCloseTime());
		shop.setRegularHoliday(regularHoliday);
		shop.setPrice(shopRegisterForm.getPrice());
		shop.setSeats(shopRegisterForm.getSeats());
		shop.setShopSite(shopRegisterForm.getShopSite());

		shopRepository.save(shop);
	}

	@Transactional
	public void update(ShopEditForm shopEditForm) {
		Shop shop = shopRepository.getReferenceById(shopEditForm.getId());
		MultipartFile imageFile = shopEditForm.getImageFile();

		if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			shop.setImageName(hashedImageName);
		}
		//コンマ区切りの曜日を取得
		String[] holidays = shopEditForm.getRegularHoliday().split(",");
		//日本語曜日に変換し、再度コンマ区切りのStringにまとめる
		String regularHoliday = Arrays.stream(holidays)
				.map(this::convertToJapaneseDay)
				.collect(Collectors.joining(","));
		
		shop.setName(shopEditForm.getName());
		shop.setCategoriesId(shopEditForm.getCategoriesId());
		shop.setDescription(shopEditForm.getDescription());
		shop.setPostalCode(shopEditForm.getPostalCode());
		shop.setAddress(shopEditForm.getAddress());
		shop.setPhoneNumber(shopEditForm.getPhoneNumber());
		shop.setOpenTime(shopEditForm.getOpenTime());
		shop.setCloseTime(shopEditForm.getCloseTime());
		shop.setRegularHoliday(regularHoliday);
		shop.setPrice(shopEditForm.getPrice());
		shop.setSeats(shopEditForm.getSeats());
		shop.setShopSite(shopEditForm.getShopSite());

		shopRepository.save(shop);
	}

	//UUIDを使って生成したファイル名を返す
	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");
		for (int i = 0; i < fileNames.length - 1; i++) {
			fileNames[i] = UUID.randomUUID().toString();
		}
		String hashedFileName = String.join(".", fileNames);
		return hashedFileName;
	}

	//画像ファイルを指定したファイルにコピーする
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {

			Files.copy(imageFile.getInputStream(), filePath);

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void writeShopsToCsv(Writer writer) {
		List<Shop> shops = shopRepository.findAll();
		try (CSVWriter csvWriter = new CSVWriter(writer)) {
			//ファイルに書き込み
			csvWriter.writeNext(new String[] { "ID", "カテゴリ－", "店舗名", "説明", "郵便番号", "住所", "電話番号", "開店時間", "閉店時間", "定休日",
					"価格帯", "座席数", "Webサイト", "登録日", "更新日" });
			for (Shop shop : shops) {
				csvWriter.writeNext(new String[] {
						String.valueOf(shop.getId()),
						String.valueOf(shop.getCategoriesId()),
						shop.getName(),
						shop.getDescription(),
						shop.getPostalCode(),
						shop.getAddress(),
						shop.getPhoneNumber(),
						shop.getOpenTime(),
						shop.getCloseTime(),
						shop.getRegularHoliday(),
						String.valueOf(shop.getPrice()),
						String.valueOf(shop.getSeats()),
						shop.getShopSite(),
						String.valueOf(shop.getCreatedAt()),
						String.valueOf(shop.getUpdatedAt())
				});
			}
		} catch (Exception e) {
			throw new RuntimeException("CSV書き込み中にエラーが発生しました。", e);
		}
	}

	private String convertToJapaneseDay(String day) {
		switch (day) {
		case "Sunday":
			return "日曜日";
		case "Monday":
			return "月曜日";
		case "Tuesday":
			return "火曜日";
		case "Wednesday":
			return "水曜日";
		case "Thursday":
			return "木曜日";
		case "Friday":
			return "金曜日";
		case "Saturday":
			return "土曜日";
		default:
			return day;
		}
	}
}
