package com.app.petcare.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	@Column
	private String addrsLine1;
	
	@Column
	private String addrsLine2;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String pincode;
	
}
