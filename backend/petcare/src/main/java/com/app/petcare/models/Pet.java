package com.app.petcare.models;

import java.time.LocalDateTime;

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
public class Pet {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petId;
	
	@Column
	private String petName;
	
	@Column
	private String breed;
	
	@Column
	private Integer age;
	
	@Column
	private String medicalHistory;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updatedAt;

}