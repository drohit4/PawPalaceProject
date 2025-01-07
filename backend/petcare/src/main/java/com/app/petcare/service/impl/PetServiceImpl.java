package com.app.petcare.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.PetDTO;
import com.app.petcare.dto.UserDTO;
import com.app.petcare.exceptions.ResourceNotFoundException;
import com.app.petcare.models.Pet;
import com.app.petcare.models.User;
import com.app.petcare.repository.PetRepository;
import com.app.petcare.repository.UserRepository;
import com.app.petcare.service.PetService;

import jakarta.transaction.Transactional;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private UserRepository userRepository;

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

	/*
	 * PetDTO will Have user : Two Options now : 1. Retrive the user if present and
	 * then save the data OR 2. If User not present then save user first then saved
	 * pet data
	 */
	@Override
	@Transactional
	public PetDTO createPetProfile(PetDTO petdto) {
		Optional<User> user = this.userRepository.findByEmail(petdto.getUser().getEmail());

		Pet pet = petDtoToPet(petdto); // Convert DTO to entity
		pet.setCreatedAt(LocalDateTime.now()); // Set created timestamp
		pet.setUpdatedAt(LocalDateTime.now()); // Set updated timestamp

		if (user.isPresent()) {
			User savedUser = user.get(); // Use the existing user, no need to save again
			pet.setUser(savedUser); // Associate the pet with the user
		} else {
			User savedUser = this.userRepository.save(pet.getUser());
			pet.setUser(savedUser);
		}
		pet = this.petRepository.save(pet); // Save the pet entity
		return this.modelMapper.map(pet, PetDTO.class); // Map the saved entity back to DTO
	}

	@Transactional
	@Override
	public PetDTO updatePetData(Long petId, PetDTO petdto) {
	    Pet pet = this.petRepository.findById(petId)
	            .orElseThrow(() -> new ResourceNotFoundException("Pet Data not found with Id : " + petId));
	    
	    if (petdto.getUser() != null) {
	        Optional<User> savedUser = this.userRepository.findByEmail(petdto.getUser().getEmail());
	        savedUser.ifPresent(pet::setUser);
	    }
	    pet = updateSavedPetDate(pet, petdto);
	    pet = this.petRepository.save(pet);
	    return this.modelMapper.map(pet, PetDTO.class);
	}
	@Override
	public DeleteResponse deletePetDate(Long petId) {
		Pet pet = this.petRepository.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException("{Pet Data Not Exist for the Given Id :" + petId));
		this.petRepository.delete(pet);
		return new DeleteResponse(LocalDateTime.now(), "Pet Data Deleted successfully with id :" + petId);
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

	private Pet updateSavedPetDate(Pet savedPet, PetDTO petDTO) {
		savedPet.setAge(petDTO.getAge());
		savedPet.setBreed(petDTO.getBreed());
		savedPet.setMedicalHistory(savedPet.getMedicalHistory() + "\n" + petDTO.getMedicalHistory());
		savedPet.setPetName(petDTO.getPetName());
		savedPet.setUpdatedAt(LocalDateTime.now());
		return savedPet;
	}

}
