package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationEditForm {
	private Integer id;
	
	private Integer shopId;
	
	private Integer userId;
	
	@NotBlank(message = "予約日を選択してください。")
	private String reservationDate;

	@NotBlank(message = "予約時間を選択してください。")
	private String reservationTime;

	@NotNull(message = "予約人数を入力してください。")
	@Min(value = 1, message = "予約人数は1人以上に設定してください。")
	private Integer reservationCount;

}
