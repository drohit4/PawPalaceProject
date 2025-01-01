package com.app.petcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petcare.dto.PetDTO;
import com.app.petcare.models.Pet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/pets")
public class PetController {
	
	/**
     * Get all pets.
     * 
     * @return ResponseEntity containing a list of all pets
     */
    @Operation(summary = "RETRIEVE_ ALL_PETS", 
               description = "Fetches a list of all pets in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Successfully retrieved the list of pets", 
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = Pet.class))),
        @ApiResponse(responseCode = "404", 
                     description = "No pets found", 
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Pet>> retrieveAllPets() {        
    	 return ResponseEntity.status(HttpStatus.OK).body(null);    }
	
    /**
     * Persists pet data into the database.
     *
     * @param petdto The pet data to be saved
     * @return ResponseEntity containing the saved pet data and status
     */
	@Operation(summary = "SAVE-PET-DATA", description = "Persists the pet data into the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "PET_DATA_SUCCESSFULLY_SAVED_IN_DATABASE") })
	@PostMapping
	public ResponseEntity<PetDTO> savePetData(@Valid @RequestBody PetDTO petdto) {
		// Simulate saving the pet data (replace with actual service call)
		return ResponseEntity.status(HttpStatus.OK).body(petdto);
	}
}
