package com.app.petcare.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.UserDTO;
import com.app.petcare.exceptions.ResourceNotFoundException;
import com.app.petcare.exceptions.UserAlreadyExistsException;
import com.app.petcare.exceptions.UserNotFoundException;
import com.app.petcare.models.User;
import com.app.petcare.repository.UserRepository;
import com.app.petcare.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> retriveAllUsers() {
        List<User> userList = this.userRepository.findAll();
        if (userList.isEmpty()) {
            throw new ResourceNotFoundException("No Users are present in the database.");
        }
        return convertUsersToDTOs(userList);
    }

    @Override
    public UserDTO fetchUserById(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User Found with the given ID: " + userId));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    @Override
    public UserDTO createUserData(UserDTO userDTO) {
        if (this.userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with the given email: " + userDTO.getEmail());
        }
        User user = this.modelMapper.map(userDTO, User.class);
        user = this.userRepository.save(user);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    @Override
    public UserDTO updateUserData(Long userId, UserDTO userDTO) {
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User Found with the given ID: " + userId));
        mapUpdatedUserData(existingUser, userDTO);
        existingUser = this.userRepository.save(existingUser);
        return this.modelMapper.map(existingUser, UserDTO.class);
    }

    @Transactional
    @Override
    public DeleteResponse deleteUserData(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for the given ID: " + userId));
        this.userRepository.delete(user);
        return new DeleteResponse(LocalDateTime.now(), "User successfully deleted with ID: " + userId);
    }

    /************************************** Helper Methods **************************************/
    private List<UserDTO> convertUsersToDTOs(List<User> userList) {
        return userList.stream().map(user -> this.modelMapper.map(user, UserDTO.class)).toList();
    }

    private void mapUpdatedUserData(User existingUser, UserDTO userDTO) {
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setMobNumber(userDTO.getMobNumber());
    }
}
