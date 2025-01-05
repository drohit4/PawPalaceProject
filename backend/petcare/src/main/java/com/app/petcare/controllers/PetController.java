package com.app.petcare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.PetDTO;
import com.app.petcare.models.Pet;
import com.app.petcare.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.Delegate;

@RestController
@Validated
@RequestMapping("/api/pets")
public class PetController {
	
	@Autowired
	private PetService petService;

	/**
	 * Get all pets.
	 * 
	 * @return ResponseEntity containing a list of all pets
	 */
	@Operation(summary = "Retrieve All Pets", description = "Fetches a list of all pets in the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))),
			@ApiResponse(responseCode = "404", description = "No pets found", content = @Content) })
	@GetMapping
	public ResponseEntity<List<PetDTO>> retrieveAllPets() {
		List<PetDTO> petList = this.petService.getAllPetData();
		return ResponseEntity.status(HttpStatus.OK).body(petList);
	}
	
	/**
	 * Get Pets Data by id.
	 * 
	 * @return ResponseEntity containing a list of all pets
	 */
	@Operation(summary = "Retrieve Pet Data using Id", description = "Fetches a Pet Data using pet_id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the pet data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))),
			@ApiResponse(responseCode = "404", description = "No pet data found", content = @Content) })
	@GetMapping("/{petId}")
	public ResponseEntity<PetDTO> retrievePetDataById(@PathVariable Long petId) {
		PetDTO pet = this.petService.getPetDataById(petId);
		return ResponseEntity.status(HttpStatus.OK).body(pet);
	}

	/**
	 * Persists pet data into the database.
	 *
	 * @param petdto The pet data to be saved
	 * @return ResponseEntity containing the saved pet data and status
	 */
	@Operation(summary = "Save Pet Data", description = "Persists the pet data into the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Pet data successfully saved in the database", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))),
			@ApiResponse(responseCode = "404", description = "Error Occured At Saving the Data", content = @Content)
	})
	@PostMapping
	public ResponseEntity<PetDTO> savePetData(@Valid @RequestBody PetDTO petdto) {
		PetDTO pet = this.petService.createPetProfile(petdto);
		return ResponseEntity.status(HttpStatus.CREATED).body(pet);
	}

	/**
	 * Update pet data in the database.
	 *
	 * @param petId  The ID of the pet to be updated
	 * @param petdto The pet data to be updated
	 * @return ResponseEntity containing the updated pet data and status
	 */
	@Operation(summary = "Update Pet Data", description = "Updates the pet data in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pet data successfully updated in the database", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))),
			@ApiResponse(responseCode = "404", description = "Pet not found", content = @Content) })
	@PutMapping("/{petId}")
	public ResponseEntity<PetDTO> updatePetData(@PathVariable Long petId, @Valid @RequestBody PetDTO petdto) {
		PetDTO  petDTO = this.petService.updatePetData(petId, petdto);
		return ResponseEntity.status(HttpStatus.OK).body(petDTO);
	}

	/**
	 * Delete pet data in the database.
	 *
	 * @param petId  The ID of the pet to be updated
	 * @return ResponseEntity containing the deleted pet data and status
	 */
	@Operation(summary = "Update Pet Data", description = "Updates the pet data in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pet data successfully updated in the database", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))),
			@ApiResponse(responseCode = "404", description = "Pet not found", content = @Content) })
	@DeleteMapping("/{petId}")
	public ResponseEntity<DeleteResponse> detelePetData(@PathVariable Long petId){
		DeleteResponse deleteResponse = this.petService.deletePetDate(petId);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
	}
}
