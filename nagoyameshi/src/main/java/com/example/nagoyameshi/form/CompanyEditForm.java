package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyEditForm {
	@NotNull
	private Integer id;

	@NotBlank(message = "会社名を入力してください。")
	private String name;

	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "所在地を入力してください。")
	private String location;

	@NotNull(message = "代表者を入力してください。")
	private String representative;

	@NotNull(message = "設立日を入力してください。")
	private String establishment;
	
	@NotBlank(message = "資本金を入力してください。")
	private String capital;
	
	@NotBlank(message = "概要を入力してください。")
	private String content;

}
