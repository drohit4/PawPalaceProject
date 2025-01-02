package com.app.petcare.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.PetDTO;
import com.app.petcare.exceptions.ResourceNotFoundException;
import com.app.petcare.models.Pet;
import com.app.petcare.repository.PetRepository;
import com.app.petcare.service.PetService;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PetRepository petRepository;

	@Override
	public List<PetDTO> getAllPetData() {
		List<Pet> getAllPets = this.petRepository.findAll();
		if (getAllPets.isEmpty())
			throw new ResourceNotFoundException("No Pet Data Found...!!!");
		return petListToPetDTOList(getAllPets);
	}

	@Override
	public PetDTO getPetDataById(Long petId) {
		Pet pet = this.petRepository.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException("Pet Data not found for the given id : " + petId));
		return this.modelMapper.map(pet, PetDTO.class);
	}

	@Override
	@Transactional
	public PetDTO createPetProfile(PetDTO petdto) {
		Pet pet = petDtoToPet(petdto); // Convert DTO to entity
		pet.setCreatedAt(LocalDateTime.now()); // Set created timestamp
		pet.setUpdatedAt(LocalDateTime.now()); // Set updated timestamp

		pet = this.petRepository.save(pet); // Save the pet entity

		return this.modelMapper.map(pet, PetDTO.class); // Map the saved entity back to DTO
	}

	@Transactional
	@Override
	public PetDTO updatePetData(Long petId, PetDTO petdto) {
		Pet pet = this.petRepository.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException("Pet Data not found with Id : " + petId));
		return null;
	}

	@Override
	public DeleteResponse deletePetDate(Long petId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*-------------------------------------Helper-Method-Area-----------------------------------------------------------*/
	private PetDTO petTopetDTO(Pet pet) {
		return this.modelMapper.map(pet, PetDTO.class);

	}

	private Pet petDtoToPet(PetDTO petDTO) {
		return this.modelMapper.map(petDTO, Pet.class);
	}

	private List<PetDTO> petListToPetDTOList(List<Pet> petList) {
		return petList.stream().map(item -> this.modelMapper.map(item, PetDTO.class)).toList();
	}

	private List<Pet> petListDtoToPetList(List<PetDTO> petDtoList) {
		return petDtoList.stream().map(item -> this.modelMapper.map(item, Pet.class)).toList();
	}

}
