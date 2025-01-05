package com.app.petcare.dto;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

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
public class UserDTO {
	
	private Long userId;
	
	@NotBlank(message = "username is required")
	@Size(min = 5,max = 50,message = "username should be minimum 20 and maximum 50 character")
	private String username;
	
	@NotBlank(message = "password is required")
	@Size(min = 8,message = "password should be minimum 8 and maximum 50 character")
	private String password;
	
	@Email
	@Size(max = 50)
	private String email;
	
	@NumberFormat(style = Style.NUMBER)
	@Size(min = 10,max = 10)
	private String mobNumber;
}
