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
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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
	@Operation(summary = "Retrieve all pets", description = "Fetches a list of all pets in the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class), examples = @ExampleObject(value = "[{ \"id\": 1, \"name\": \"Buddy\", \"age\": 3, \"species\": \"Dog\" }]"))),
			@ApiResponse(responseCode = "404", description = "No pets found", content = @Content) })
	@GetMapping
	public ResponseEntity<List<PetDTO>> retrieveAllPets() {
		List<PetDTO> petList = this.petService.getAllPetData();
		return ResponseEntity.status(HttpStatus.OK).body(petList);
	}
	
	/**
	 * Get Pet data by ID.
	 * 
	 * @param petId The ID of the pet to be retrieved
	 * @return ResponseEntity containing the pet data
	 */
	@Operation(summary = "Retrieve pet data by ID", description = "Fetches pet data by pet ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the pet data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class), examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Buddy\", \"age\": 3, \"species\": \"Dog\" }"))),
			@ApiResponse(responseCode = "404", description = "No pet found with the provided ID", content = @Content) })
	@GetMapping("/{petId}")
	public ResponseEntity<PetDTO> retrievePetDataById(@PathVariable Long petId) {
		PetDTO pet = this.petService.getPetDataById(petId);
		return ResponseEntity.status(HttpStatus.OK).body(pet);
	}

	/**
	 * Save pet data into the database.
	 *
	 * @param petdto The pet data to be saved
	 * @return ResponseEntity containing the saved pet data and status
	 */
	@Operation(summary = "Save pet data", description = "Saves the pet data into the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Pet data successfully saved in the database", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class), examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Buddy\", \"age\": 3, \"species\": \"Dog\" }"))),
			@ApiResponse(responseCode = "400", description = "Error occurred while saving the pet data", content = @Content) })
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
	@Operation(summary = "Update pet data", description = "Updates the pet data in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pet data successfully updated in the database", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class), examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Buddy\", \"age\": 4, \"species\": \"Dog\" }"))),
			@ApiResponse(responseCode = "404", description = "Pet not found with the provided ID", content = @Content) })
	@PutMapping("/{petId}")
	public ResponseEntity<PetDTO> updatePetData(@PathVariable Long petId, @Valid @RequestBody PetDTO petdto) {
		PetDTO petDTO = this.petService.updatePetData(petId, petdto);
		return ResponseEntity.status(HttpStatus.OK).body(petDTO);
	}

	/**
	 * Delete pet data from the database.
	 *
	 * @param petId The ID of the pet to be deleted
	 * @return ResponseEntity containing the deletion status
	 */
	@Operation(summary = "Delete pet data", description = "Deletes the pet data from the database using the pet ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pet data successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class), examples = @ExampleObject(value = "{ \"message\": \"Pet data deleted successfully\", \"status\": \"SUCCESS\" }"))),
			@ApiResponse(responseCode = "404", description = "Pet not found with the provided ID", content = @Content) })
	@DeleteMapping("/{petId}")
	public ResponseEntity<DeleteResponse> deletePetData(@PathVariable Long petId) {
		DeleteResponse deleteResponse = this.petService.deletePetDate(petId);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
	}
}
