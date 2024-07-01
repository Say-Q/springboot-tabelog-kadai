package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

	// 無料会員の予約一覧での表示。（現在の日付より）
	@Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.reservationDate >= :currentDate ORDER BY r.createdAt DESC")
	Page<Reservation> findByUserAndReservationDateGreaterThanEqualOrderByCreatedAtDesc(@Param("user") User user,
			@Param("currentDate") String currentDate, Pageable pageable);
}
