package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	public Page<Reservation> findByUserOrderByReservationDateAsc(User user, Pageable pageable);

	public Page<Reservation> findByUserOrderByReservationDateDesc(User user, Pageable pageable);

	// 予約一覧での表示。（現在の日付以降）
	@Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.reservationDate >= :currentDate ORDER BY r.reservationDate ASC")
	Page<Reservation> findByUserAndReservationDateGreaterThanEqualOrderByReservationDateAsc(@Param("user") User user,
			@Param("currentDate") String currentDate, Pageable pageable);

	@Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.reservationDate >= :currentDate ORDER BY r.reservationDate DESC")
	Page<Reservation> findByUserAndReservationDateGreaterThanEqualOrderByReservationDateDesc(@Param("user") User user,
			@Param("currentDate") String currentDate, Pageable pageable);
	
	//ユーザーIDに基づいて予約データを削除するメソッド
	@Modifying
	@Transactional
	@Query("DELETE FROM Reservation r WHERE r.user.id = :userId")
	void deleteByUserId(@Param("userId") Integer userId);
}
