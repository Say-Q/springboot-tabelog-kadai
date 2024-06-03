package com.example.nagoyameshi.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shops")
@Data
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "categories_id")
	private Integer categoriesId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name ="image_name")
	private String imageName;
	
	//説明
	@Column(name = "description")
	private String description;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "open_time")
	private String openTime;
	
	@Column(name = "close_time")
	private String closeTime;
	
	@Column(name = "regular_holiday")
	private String regularHoliday;
	
	@Column(name = "price")
	private Integer price;
	//座席数
	@Column(name = "seats")
	private Integer seats;
	
	@Column(name = "shop_site")
	private String shopSite;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
}
