package com.example.nagoyameshi.entity;

public class ReservationDTO {
	private Integer id;

	private Shop shop;

	private String reservationDate;

	private boolean pastReservation;

	private String reservationTime;

	private Integer reservationCount;

	// 各種getterとsetter

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public boolean isPastReservation() {
		return pastReservation;
	}

	public void setPastReservation(boolean pastReservation) {
		this.pastReservation = pastReservation;
	}

	public String getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}

	public Integer getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Integer reservationCount) {
		this.reservationCount = reservationCount;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
