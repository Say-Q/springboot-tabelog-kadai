package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRegisterForm {
	@NotBlank(message = "店舗名を入力してください。")
	private String name;

	private MultipartFile imageFile;

	@NotNull(message = "カテゴリーを選択してください。")
	private Integer categoriesId;

	@NotBlank(message = "説明を入力してください。")
	private String description;

	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;

	@NotBlank(message = "住所を入力してください。")
	private String address;

	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;

//	@NotNull(message = "開店時間を入力してください。")
//	@Pattern(^([01][0-9]|2[0-3]):[0-5][0-9]$)
//	private String openTime;

//	@NotNull(message = "開店時間を入力してください。")
//	private Time openTime;

	@NotNull(message = "開店時間を入力してください。")
	private String openTime;

//	@NotNull(message = "閉店時間を入力してください。")
//	private Time closeTime;

	@NotNull(message = "閉店時間を入力してください。")
	private String closeTime;

	@NotBlank(message = "定休日を入力してください。")
	private String regularHoliday;

	@NotNull(message = "価格帯を入力してください。")
	@Min(value = 10, message = "価格帯は10円以上に設定してください。")
	private Integer price;

	@NotNull(message = "座席数を入力してください。")
	private Integer seats;

	private String shopSite;
}
