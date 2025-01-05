package com.app.petcare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.AddressDTO;
import com.app.petcare.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Operation(summary = "GET_ALL_ADDRESSES", description = "FETCH_ALL_THE_ADDRESSES_FROM_DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_ALL_ADDRESSES", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class), examples = @ExampleObject(value = "[{ \"addressId\": 1, \"addrsLine1\": \"123 Main St\", \"addrsLine2\": \"Apt 4B\", \"city\": \"Springfield\", \"country\": \"USA\", \"pincode\": \"12345\" }]"))),
			@ApiResponse(responseCode = "404", description = "NO_ADDRESSES_FOUND", content = @Content) })
	@GetMapping
	public ResponseEntity<List<AddressDTO>> retrieveAllAddresses() {
		List<AddressDTO> addressList = this.addressService.retriveAllAddress();
		return ResponseEntity.status(HttpStatus.OK).body(addressList);
	}

	@Operation(summary = "GET_ADDRESS_BY_ID", description = "FETCH_ADDRESS_DATA_USING_THE_ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_ADDRESS_BY_ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class), examples = @ExampleObject(value = "{ \"addressId\": 1, \"addrsLine1\": \"123 Main St\", \"addrsLine2\": \"Apt 4B\", \"city\": \"Springfield\", \"country\": \"USA\", \"pincode\": \"12345\" }"))),
			@ApiResponse(responseCode = "404", description = "NO_ADDRESS_FOUND_WITH_ID", content = @Content) })
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressDTO> retrieveAddressById(@PathVariable Long addressId) {
		AddressDTO address = this.addressService.retriveAddressById(addressId);
		return ResponseEntity.status(HttpStatus.OK).body(address);
	}

	@Operation(summary = "CREATE_NEW_ADDRESS", description = "SAVE_NEW_ADDRESS_IN_THE_DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "SUCCESSFULLY_SAVED_ADDRESS_DATA", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class), examples = @ExampleObject(value = "{ \"addressId\": 1, \"addrsLine1\": \"123 Main St\", \"addrsLine2\": \"Apt 4B\", \"city\": \"Springfield\", \"country\": \"USA\", \"pincode\": \"12345\" }"))),
			@ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content) })
	@PostMapping
	public ResponseEntity<AddressDTO> saveAddress(@Valid @RequestBody AddressDTO addressDTO) {
		AddressDTO savedAddress = this.addressService.createAddress(addressDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
	}

	@Operation(summary = "UPDATE_EXISTING_ADDRESS", description = "UPDATE_EXISTING_ADDRESS_DATA")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "SUCCESSFULLY_UPDATED_ADDRESS_DATA", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class), examples = @ExampleObject(value = "{ \"addressId\": 1, \"addrsLine1\": \"123 Main St\", \"addrsLine2\": \"Apt 4C\", \"city\": \"Springfield\", \"country\": \"USA\", \"pincode\": \"12345\" }"))),
			@ApiResponse(responseCode = "404", description = "ADDRESS_NOT_FOUND_WITH_ID", content = @Content) })
	@PutMapping("/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
			@Valid @RequestBody AddressDTO addressDTO) {
		AddressDTO updatedAddress = this.addressService.updatedAddress(addressId, addressDTO);
		return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
	}

	@Operation(summary = "DELETE_EXISTING_ADDRESS", description = "DELETE_ADDRESS_DATA")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "SUCCESSFULLY_DELETED_ADDRESS_DATA", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class), examples = @ExampleObject(value = "{ \"message\": \"ADDRESS_DELETED_SUCCESSFULLY\", \"status\": \"success\" }"))),
			@ApiResponse(responseCode = "404", description = "ADDRESS_NOT_FOUND_WITH_ID", content = @Content) })
	@DeleteMapping("/{addressId}")
	public ResponseEntity<DeleteResponse> deleteAddress(@PathVariable Long addressId) {
		DeleteResponse deleteResponse = this.addressService.deleteAddressById(addressId);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
	}
}
