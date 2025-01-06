package com.app.petcare.dto;

import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Validated
@JsonInclude(Include.NON_NULL)  // Only include non-null fields
public class PetDTO {

    private Long petId;

    @NotBlank(message = "Pet name is required.")
    @Size(max = 50, message = "Pet name cannot exceed 50 characters.")
    private String petName;

    @NotBlank(message = "Breed is required.")
    @Size(max = 30, message = "Breed cannot exceed 30 characters.")
    private String breed;

    @NotNull(message = "Age is required.")
    private Integer age;

    @Size(max = 500, message = "Medical history cannot exceed 500 characters.")
    private String medicalHistory;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "Update date cannot be in the future.")
    private LocalDateTime updatedAt;

    @JsonBackReference
    private UserDTO user;
}
