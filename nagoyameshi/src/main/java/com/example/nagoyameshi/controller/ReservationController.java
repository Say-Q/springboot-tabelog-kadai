package com.example.nagoyameshi.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.ReservationDTO;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationEditForm;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;

import jakarta.persistence.EntityNotFoundException;

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
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "reservationDate", direction = Direction.DESC) Pageable pageable,
			Model model) {

		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage;

		reservationPage = orderReservationDate(userDetailsImpl, order, pageable);

		// Reservation を ReservationDTO に変換
		List<ReservationDTO> reservationDTOs = reservationPage.stream().map(reservation -> {
			ReservationDTO reservationDTO = new ReservationDTO();
			reservationDTO.setId(reservation.getId());
			reservationDTO.setShop(reservation.getShop());
			reservationDTO.setReservationDate(LocalDate.parse(reservation.getReservationDate()).toString()); // Str -> LocalDate
			reservationDTO.setReservationTime(reservation.getReservationTime());
			reservationDTO.setReservationCount(reservation.getReservationCount());
			reservationDTO.setPastReservation(isPastReservation(reservationDTO.getReservationDate()));
			return reservationDTO;
		}).collect(Collectors.toList());

		model.addAttribute("order", order);
		model.addAttribute("reservationPage", reservationPage);
		model.addAttribute("reservationDTOs", reservationDTOs);

		return "reservation/index";

	}

	public Page<Reservation> orderReservationDate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			String order, Pageable pageable) {
		Page<Reservation> reservationPage;
		User user = userDetailsImpl.getUser();

		boolean isOrder = Objects.isNull(order) || order.isEmpty() || order.equals("reservationdateDesc");
		String currentDate = LocalDate.now().toString(); // LocalDate を String に変換

		return reservationPage = isOrder
				? reservationRepository.findByUserAndReservationDateGreaterThanEqualOrderByReservationDateDesc(user,
						currentDate, pageable)
				: reservationRepository.findByUserAndReservationDateGreaterThanEqualOrderByReservationDateAsc(user,
						currentDate, pageable);

	}

	//予約変更ボタン表示切り替えの為、過去日判断
	private boolean isPastReservation(CharSequence reservationDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 適切なパターンを指定
		LocalDate date = LocalDate.parse(reservationDate, formatter);

		return date.isBefore(LocalDate.now());
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
			while (!start.equals(LocalTime.MIDNIGHT)) {
				times.add(start.toString());
				start = start.plusMinutes(30); //30分間隔で時間を生成
			}

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

		model.addAttribute("shop", shop);

		if (bindingResult.hasErrors()) {

			List<String> availableTimes = generateAvailableTimes(shop.getOpenTime(), shop.getCloseTime());
			List<Integer> availableCounts = generateAvailableCounts(shop.getSeats());

			model.addAttribute("availableTimes", availableTimes);
			model.addAttribute("availableCounts", availableCounts);
			redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);

			return "reservation/register";

		}

		LocalDate reservationDate = LocalDate.parse(reservationInputForm.getReservationDate());
		LocalTime reservationTime = LocalTime.parse(reservationInputForm.getReservationTime());

		// 定休日チェック
		if (!isAvailableForBooking(id, reservationDate)) {
			String dayOfWeekJapanese = DAY_OF_WEEK_JAPANESE.get(reservationDate.getDayOfWeek());
			String errorMessage = String.format("%d年%d月%d日（%s）は、定休日のため予約できません。",
					reservationDate.getYear(), reservationDate.getMonthValue(), reservationDate.getDayOfMonth(),
					dayOfWeekJapanese);

			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/reservation/" + id;
		}

		// 予約時間チェック
		ZoneId japanZoneId = ZoneId.of("Asia/Tokyo");
		ZonedDateTime currentZonedDateTime = ZonedDateTime.now(japanZoneId);
		LocalDate currentDate = currentZonedDateTime.toLocalDate();
		LocalTime currentTime = currentZonedDateTime.toLocalTime();

		if (reservationDate.equals(currentDate) && reservationTime.isBefore(currentTime)) {
			String errorMessage = "本日は時間が過ぎているため、予約できません。";

			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/reservation/" + id;
		}

		//重複チェック
		List<Reservation> existingReservation = reservationRepository
				.findByUserIdAndShopIdAndReservationDateAndReservationTime(
						userDetailsImpl.getUser().getId(), id, reservationInputForm.getReservationDate(),
						reservationInputForm.getReservationTime());

		if (!existingReservation.isEmpty()) {
			String dayOfWeekJapanese = DAY_OF_WEEK_JAPANESE.get(reservationDate.getDayOfWeek());
			String errorMessage = String.format("%d年%d月%d日（%s）は、既に予約済みです。", reservationDate.getYear(),
					reservationDate.getMonthValue(), reservationDate.getDayOfMonth(), dayOfWeekJapanese);
			
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/reservation/" + id;
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
		Shop shop = shopRepository.getReferenceById(reservation.getShop().getId());

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
		model.addAttribute("availableTimes", availableTimes);
		model.addAttribute("availableCounts", availableCounts);
		model.addAttribute("reservationEditForm", reservationEditForm);

		return "reservation/edit";
	}

	@PostMapping("/reservation/{id}/update")
	public String update(Integer id, @ModelAttribute @Validated ReservationEditForm reservationEditForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		Reservation reservation = reservationRepository.getReferenceById(id);
		LocalDate reservationDate = LocalDate.parse(reservationEditForm.getReservationDate());
		LocalTime reservationTime = LocalTime.parse(reservationEditForm.getReservationTime());

		if (bindingResult.hasErrors()) {

			return "reservation/edit";

		}

		// 定休日チェック
		if (!isAvailableForBooking(reservationEditForm.getShopId(), reservationDate)) {
			String dayOfWeekJapanese = DAY_OF_WEEK_JAPANESE.get(reservationDate.getDayOfWeek());
			String errorMessage = String.format("%d年%d月%d日（%s）は、定休日のため予約できません。",
					reservationDate.getYear(), reservationDate.getMonthValue(), reservationDate.getDayOfMonth(),
					dayOfWeekJapanese);

			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/reservation/" + id + "/edit";
		}

		// 予約時間チェック
		ZoneId japanZoneId = ZoneId.of("Asia/Tokyo");
		ZonedDateTime currentZonedDateTime = ZonedDateTime.now(japanZoneId);
		LocalDate currentDate = currentZonedDateTime.toLocalDate();
		LocalTime currentTime = currentZonedDateTime.toLocalTime();

		if (reservationDate.equals(currentDate) && reservationTime.isBefore(currentTime)) {
			String errorMessage = "本日は時間が過ぎているため、予約変更できません。";

			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/reservation/" + id + "/edit";
		}

		reservationEditForm.setShopId(reservation.getShop().getId());
		reservationEditForm.setUserId(reservation.getUser().getId());
		reservationService.update(reservationEditForm);
		redirectAttributes.addFlashAttribute("successMessage",
				"『" + reservation.getShop().getName() + "』：" + reservation.getReservationDate() + "の予約を変更しました。");

		return "redirect:/reservation";
	}

	private static final Map<DayOfWeek, String> DAY_OF_WEEK_JAPANESE = Map.of(
			DayOfWeek.MONDAY, "月曜日",
			DayOfWeek.TUESDAY, "火曜日",
			DayOfWeek.WEDNESDAY, "水曜日",
			DayOfWeek.THURSDAY, "木曜日",
			DayOfWeek.FRIDAY, "金曜日",
			DayOfWeek.SATURDAY, "土曜日",
			DayOfWeek.SUNDAY, "日曜日");

	//定休日チェック
	public boolean isAvailableForBooking(Integer shopId, LocalDate reservationDate) {
		Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new EntityNotFoundException("Shop not found"));
		// 定休日のリストをカンマで分割して取得
		List<String> holidays = Arrays.asList(shop.getRegularHoliday().split(","));

		// 予約日の曜日を日本語で取得
		DayOfWeek reservationDay = reservationDate.getDayOfWeek();
		String reservationDayJapanese = DAY_OF_WEEK_JAPANESE.get(reservationDay);

		// 通常の曜日の定休日をチェック
		if (holidays.contains(reservationDayJapanese)) {
			return false; // 予約日は通常の定休日に該当
		}

		return true; // 予約可能
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
