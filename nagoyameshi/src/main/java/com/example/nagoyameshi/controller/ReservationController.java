package com.example.nagoyameshi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationEditForm;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final ShopRepository shopRepository;
	private final ReservationService reservationService;

	public ReservationController(ReservationRepository reservationRepository, ShopRepository shopRepository,
			ReservationService reservationService) {
		this.reservationRepository = reservationRepository;
		this.shopRepository = shopRepository;
		this.reservationService = reservationService;
	}

	@GetMapping("/reservation")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {

		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage;

		if ("2".equals(user.getRole())) {

			reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);

		} else {

			String currentDate = LocalDate.now().toString(); // LocalDate を String に変換
			reservationPage = reservationRepository.findByUserAndReservationDateGreaterThanEqualOrderByCreatedAtDesc(
					user, currentDate, pageable);
		}

		model.addAttribute("reservationPage", reservationPage);

		return "reservation/index";

	}

	@GetMapping("/reservation/{id}")
	public String input(@PathVariable(name = "id") Integer id,
			@ModelAttribute @Validated ReservationInputForm reservationInputForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		Shop shop = shopRepository.getReferenceById(id);

		List<String> availableTimes = generateAvailableTimes(shop.getOpenTime(), shop.getCloseTime());
		List<Integer> availableCounts = generateAvailableCounts(shop.getSeats());

		model.addAttribute("shop", shop);
		model.addAttribute("availableTimes", availableTimes);
		model.addAttribute("availableCounts", availableCounts);
		model.addAttribute("reservationInputForm", new ReservationInputForm());

		return "reservation/register";

	}

	private List<String> generateAvailableTimes(String openTime, String closeTime) {
		List<String> times = new ArrayList<>();
		LocalTime start = LocalTime.parse(openTime);
		LocalTime end = LocalTime.parse(closeTime);

		//日を跨ぐ場合の処理
		if (end.isBefore(start)) {
			while (start.isBefore(LocalTime.MAX) || start.equals(LocalTime.MAX)) {
				times.add(start.toString());
				start = start.plusMinutes(30); //30分間隔で時間を生成
			}

			start = LocalTime.MIDNIGHT;
		}

		while (start.isBefore(end) || start.equals(end)) {
			times.add(start.toString());
			start = start.plusMinutes(30); //30分間隔で時間を生成
		}

		return times;
	}

	private List<Integer> generateAvailableCounts(Integer seats) {
		List<Integer> counts = new ArrayList<Integer>();
		int maxCount = seats;

		for (int i = 1; i <= maxCount; i++) {
			counts.add(i);
		}

		return counts;
	}

	@PostMapping("reservation/{id}/register")
	public String register(@PathVariable(name = "id") Integer id,
			@ModelAttribute @Validated ReservationInputForm reservationInputForm,
			BindingResult bindingResult, ReservationRegisterForm reservationRegisterForm,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes, Model model) {

		Shop shop = shopRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		List<String> availableTimes = generateAvailableTimes(shop.getOpenTime(), shop.getCloseTime());
		List<Integer> availableCounts = generateAvailableCounts(shop.getSeats());

		model.addAttribute("shop", shop);
		model.addAttribute("availableTimes", availableTimes);
		model.addAttribute("availableCounts", availableCounts);

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
			return "reservation/register";

		}

		reservationRegisterForm.setShopId(shop.getId());
		reservationRegisterForm.setUserId(user.getId());
		reservationRegisterForm.setReservationDate(reservationInputForm.getReservationDate());
		reservationRegisterForm.setReservationTime(reservationInputForm.getReservationTime());
		reservationRegisterForm.setReservationCount(reservationInputForm.getReservationCount());

		reservationService.create(reservationRegisterForm);

		//予約完了メッセージを設定
		redirectAttributes.addFlashAttribute("message", "『" + shop.getName() + "』の予約が完了しました。");

		return "redirect:/reservation";
	}

	@GetMapping("/reservation/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Reservation reservation = reservationRepository.getReferenceById(id);
		Shop shop = shopRepository.getReferenceById(id);

		String shopName = reservation.getShop().getName();

		List<String> availableTimes = generateAvailableTimes(shop.getOpenTime(), shop.getCloseTime());
		List<Integer> availableCounts = generateAvailableCounts(shop.getSeats());

		ReservationEditForm reservationEditForm = new ReservationEditForm(
				reservation.getId(),
				reservation.getShop().getId(),
				reservation.getUser().getId(),
				reservation.getReservationDate(),
				reservation.getReservationTime(),
				reservation.getReservationCount());

		model.addAttribute("shop", shop);
		model.addAttribute("shopName", shopName);
		model.addAttribute("availableTimes", availableTimes);
		model.addAttribute("availableCounts", availableCounts);
		model.addAttribute("reservationEditForm", reservationEditForm);

		return "reservation/edit";
	}

	@PostMapping("/reservation/{id}/update")
	public String update(Integer id, @ModelAttribute @Validated ReservationEditForm reservationEditForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		Reservation reservation = reservationRepository.getReferenceById(id);
		reservationEditForm.setShopId(reservation.getShop().getId());
		reservationEditForm.setUserId(reservation.getUser().getId());

		if (bindingResult.hasErrors()) {

			return "reservation/edit";

		}

		reservationService.update(reservationEditForm);
		redirectAttributes.addFlashAttribute("successMessage",
				"『" + reservation.getShop().getName() + "』：" + reservation.getReservationDate() + "の予約を変更しました。");

		return "redirect:/reservation";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		Reservation reservation = reservationRepository.getReferenceById(id);
		reservationRepository.deleteById(id);

		String shopName = reservation.getShop().getName();

		redirectAttributes.addFlashAttribute("successMessage",
				reservation.getReservationDate() + "『" + shopName + "』" + "の予約を削除しました。");

		return "redirect:/reservation";
	}
}
