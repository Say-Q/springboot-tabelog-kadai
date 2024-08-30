package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.CompanyRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.service.ShopService;
import com.example.nagoyameshi.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;
	private final ShopService shopService;
	private final UserService userService;

	@Autowired
	private CompanyRepository companyRepository;

	public AdminController(ShopRepository shopRepository, UserRepository userRepository, ShopService shopService,
			UserService userService) {
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
		this.shopService = shopService;
		this.userService = userService;
	}

	@GetMapping
	public String index(Model model) {
		//会社情報を取得
		Company company = companyRepository.findById(1).orElse(new Company());

		model.addAttribute("company", company);

		return "admin/index";
	}

	@GetMapping("/aggregate")
	public String aggregate(@RequestParam(required = false, defaultValue = "totalCount") String type, Model model) {
		List<Map<String, Object>> monthlyData = new ArrayList<>();

		Calendar calendar = Calendar.getInstance(); //現在の日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		for (int i = 0; i < 12; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, 1); //現在の月初日に設定
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Timestamp startDate = new Timestamp(calendar.getTimeInMillis());

			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); //現在の月末日に設定
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			Timestamp endDate = new Timestamp(calendar.getTimeInMillis());

			long shopCount = type.equals("totalCount") ? shopRepository.countByCreatedAtLessThanEqual(endDate)
					: shopRepository.countByCreatedAtBetween(startDate, endDate);
			long userCount = type.equals("totalCount")
					? userRepository.countByCreatedAtLessThanEqualAndRole_IdNot(endDate, 1)
					: userRepository.countByUpdatedAtBetweenAndRole_IdNot(startDate, endDate, 1);
			long freeCount = type.equals("totalCount")
					? userRepository.countByCreatedAtLessThanEqualAndRole_Id(endDate, 2)
					: userRepository.countByCreatedAtBetweenAndRole_Id(startDate, endDate, 2);
			long payCount = type.equals("totalCount")
					? userRepository.countByUpdatedAtLessThanEqualAndRole_Id(endDate, 3)
					: userRepository.countByUpdatedAtBetweenAndRole_Id(startDate, endDate, 3);

			Integer earnings = (int) payCount * 300;

			Map<String, Object> data = new HashMap<>();
			data.put("date", sdf.format(endDate));
			data.put("shopCount", shopCount);
			data.put("userCount", userCount);
			data.put("freeCount", freeCount);
			data.put("payCount", payCount);
			data.put("earnings", earnings);

			monthlyData.add(data);

			calendar.add(Calendar.MONTH, -1); //前月末日に移動

		}

		model.addAttribute("monthlyData", monthlyData);
		model.addAttribute("type", type);

		return "admin/aggregate";
	}

	@GetMapping("/export-shopsCsv")
	public void exportShopsToCsv(HttpServletResponse response) throws IOException {
		String csvFileName = "shops.csv";
		response.setContentType("text/csv; charset=UTF-8"); //コンテンツタイプをCSV形式のファイルとして設定
		response.setHeader("Content-Disposition", "attachment; filename=\"" + csvFileName + "\"");

		// BOM (Byte Order Mark)を使用する。※ExcelなどのCSVビューアがファイルのエンコーディングをUTF-8として認識しやすくなる。
		response.getOutputStream().write(0xef);
		response.getOutputStream().write(0xbb);
		response.getOutputStream().write(0xbf);
		// UTF-8エンコーディングを指定してCSVを書き込み
		shopService.writeShopsToCsv(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
	}

	@GetMapping("/export-usersCsv")
	public void exportUsersToCsv(HttpServletResponse response) throws IOException {
		String csvFileName = "users.csv";
		response.setContentType("text/csv; charset=UTF-8"); //コンテンツタイプをCSV形式のファイルとして設定
		response.setHeader("Content-Disposition", "attachment; filename=\"" + csvFileName + "\"");

		// BOM (Byte Order Mark)を使用する。※ExcelなどのCSVビューアがファイルのエンコーディングをUTF-8として認識しやすくなる。
		response.getOutputStream().write(0xef);
		response.getOutputStream().write(0xbb);
		response.getOutputStream().write(0xbf);
		// UTF-8エンコーディングを指定してCSVを書き込み
		userService.writeUsersToCsv(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
	}

	@GetMapping("/users/aggregate")
	public String aggregate(Model model) {
		List<User> users = userRepository.findByRole_IdNot(1); //全ユーザーを取得
		//年代ごとの無料会員・有料会員の集計データを取得
		Map<String, Map<String, Integer>> memberCountByAgeGroup = userService.getMemberCountByAgeGroup(users);
		Map<String, Map<String, Integer>> memberCountByProfession = userService.getMemberCountByprofession(users);

		model.addAttribute("memberCountByAgeGroup", memberCountByAgeGroup);
		model.addAttribute("memberCountByProfession", memberCountByProfession);

		return "admin/users/aggregate";
	}
}
