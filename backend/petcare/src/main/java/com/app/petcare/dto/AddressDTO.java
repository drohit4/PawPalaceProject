package com.app.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {

    private Long addressId;

    @NotBlank(message = "Address Line 1 cannot be blank")
    @Size(max = 255, message = "Address Line 1 must not exceed 255 characters")
    private String addrsLine1;

    @Size(max = 255, message = "Address Line 2 must not exceed 255 characters")
    private String addrsLine2;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @NotBlank(message = "Pincode cannot be blank")
    @Pattern(regexp = "\\d{5,10}", message = "Pincode must be a numeric value between 5 and 10 digits")
    private String pincode;
}
