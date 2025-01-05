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
import com.app.petcare.dto.PetDTO;
import com.app.petcare.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Operation(summary = "GET_ALL_PETS", description = "FETCH_ALL_THE_PETS_FROM_DATABASE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_ALL_PETS", 
                         content = @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = PetDTO.class),
                         examples = @ExampleObject(value = "[{ \"petId\": 1, \"petName\": \"Buddy\", \"breed\": \"Dog\", \"age\": 5, \"medicalHistory\": \"No issues\", \"createdAt\": \"2025-01-01T10:00:00\", \"updatedAt\": \"2025-01-01T10:00:00\" }]"))),
            @ApiResponse(responseCode = "404", description = "NO_PETS_FOUND", content = @Content) })
    @GetMapping
    public ResponseEntity<List<PetDTO>> retrieveAllPets() {
        List<PetDTO> petList = this.petService.getAllPetData();
        return ResponseEntity.status(HttpStatus.OK).body(petList);
    }

    @Operation(summary = "GET_PET_BY_ID", description = "FETCH_PET_DATA_USING_THE_ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_RETRIEVED_PET_BY_ID", 
                         content = @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = PetDTO.class),
                         examples = @ExampleObject(value = "{ \"petId\": 1, \"petName\": \"Buddy\", \"breed\": \"Dog\", \"age\": 5, \"medicalHistory\": \"No issues\", \"createdAt\": \"2025-01-01T10:00:00\", \"updatedAt\": \"2025-01-01T10:00:00\" }"))),
            @ApiResponse(responseCode = "404", description = "NO_PET_FOUND_WITH_ID", content = @Content) })
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> retrievePetDataById(@PathVariable Long petId) {
        PetDTO pet = this.petService.getPetDataById(petId);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @Operation(summary = "CREATE_NEW_PET", description = "SAVE_NEW_PET_IN_THE_DATABASE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESSFULLY_SAVED_PET_DATA", 
                         content = @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = PetDTO.class),
                         examples = @ExampleObject(value = "{ \"petId\": 1, \"petName\": \"Buddy\", \"breed\": \"Dog\", \"age\": 5, \"medicalHistory\": \"No issues\", \"createdAt\": \"2025-01-01T10:00:00\", \"updatedAt\": \"2025-01-01T10:00:00\" }"))),
            @ApiResponse(responseCode = "400", description = "INVALID_INPUT_DATA", content = @Content) })
    @PostMapping
    public ResponseEntity<PetDTO> savePetData(@Valid @RequestBody PetDTO petdto) {
        PetDTO pet = this.petService.createPetProfile(petdto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @Operation(summary = "UPDATE_EXISTING_PET", description = "UPDATE_EXISTING_PET_DATA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_UPDATED_PET_DATA", 
                         content = @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = PetDTO.class),
                         examples = @ExampleObject(value = "{ \"petId\": 1, \"petName\": \"Buddy\", \"breed\": \"Dog\", \"age\": 6, \"medicalHistory\": \"No issues\", \"createdAt\": \"2025-01-01T10:00:00\", \"updatedAt\": \"2025-01-02T12:00:00\" }"))),
            @ApiResponse(responseCode = "404", description = "PET_NOT_FOUND_WITH_ID", content = @Content) })
    @PutMapping("/{petId}")
    public ResponseEntity<PetDTO> updatePetData(@PathVariable Long petId, @Valid @RequestBody PetDTO petdto) {
        PetDTO petDTO = this.petService.updatePetData(petId, petdto);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @Operation(summary = "DELETE_EXISTING_PET", description = "DELETE_PET_DATA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY_DELETED_PET_DATA", 
                         content = @Content(mediaType = "application/json", 
                         schema = @Schema(implementation = DeleteResponse.class),
                         examples = @ExampleObject(value = "{ \"message\": \"PET_DATA_DELETED_SUCCESSFULLY\", \"status\": \"success\" }"))),
            @ApiResponse(responseCode = "404", description = "PET_NOT_FOUND_WITH_ID", content = @Content) })
    @DeleteMapping("/{petId}")
    public ResponseEntity<DeleteResponse> detelePetData(@PathVariable Long petId){
        DeleteResponse deleteResponse = this.petService.deletePetDate(petId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
    }
}
