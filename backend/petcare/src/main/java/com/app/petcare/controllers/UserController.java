package com.app.petcare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.UserDTO;
import com.app.petcare.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Operation(summary = "GET_ALL_USERS", description = "Fetch All the Users from Database")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_ALL_USERS"),
            @ApiResponse(responseCode = "404", description = "NO_USERS_FOUND") })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = this.userService.retriveAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @Operation(summary = "GET_USER_BY_ID", description = "Fetch the User data using the Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_USER_BY_ID"),
            @ApiResponse(responseCode = "404", description = "NO_USER_FOUND_WITH_ID")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = this.userService.fetchUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @Operation(summary = "CREATE_NEW_USER", description = "Save the new user in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESSFULLY_SAVED_USER_DATA"),
            @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA")
    })
    @PostMapping
    public ResponseEntity<UserDTO> saveUserData(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = this.userService.createUserData(userDTO);  
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @Operation(summary = "UPDATE_EXISTING_USER", description = "Update the existing user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_UPDATED_USER_DATA"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND_WITH_ID")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserData(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = this.userService.updateUserData(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @Operation(summary = "DELETE_EXISTING_USER", description = "Delete the user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_DELETED_USER_DATA"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND_WITH_ID")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteResponse> deleteUserData(@PathVariable Long userId) {
        DeleteResponse deleteResponse = this.userService.deleteUserData(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
    }
}
