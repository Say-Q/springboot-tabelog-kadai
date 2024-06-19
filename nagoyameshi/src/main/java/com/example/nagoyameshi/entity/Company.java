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
@Table(name = "company")
@Data
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	//会社名
	@Column(name = "name")
	private String name;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	//所在地
	@Column(name = "location")
	private String location;
	
	//代表者
	@Column(name = "representative")
	private String representative;
	
	//設立
	@Column(name = "establishment")
	private String establishment;
	
	//資本金
	@Column(name = "capital")
	private String capital;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;	
}
