package com.app.petcare.service;

import java.util.List;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.UserDTO;
import com.app.petcare.models.User;

public interface UserService {
	
	public List<UserDTO> retriveAllUsers();
	public UserDTO fetchUserById(Long userId);
	public UserDTO createUserData(UserDTO userDTO);
	public UserDTO updateUserData(Long userId,UserDTO userDTO);
	public DeleteResponse deleteUserData(Long userId);

}
