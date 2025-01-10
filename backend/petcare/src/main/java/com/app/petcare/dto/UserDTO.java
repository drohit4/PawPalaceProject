package com.app.petcare.dto;

import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class UserDTO {

    private Long userId;

    @NotBlank(message = "username is required")
    @Size(min = 5, max = 50, message = "username should be between 5 and 50 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password should be between 8 and 50 characters")
    private String password;

    @Email
    @Size(max = 50)
    private String email;

    @NumberFormat(style = Style.NUMBER)
    @Size(min = 10, max = 10)
    private String mobNumber;

    @JsonManagedReference
    private List<PetDTO> pets;
    
    private AddressDTO address;
}
