package com.app.petcare.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

}
