package com.app.petcare.service;


import java.util.List;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.PetDTO;

public interface PetService {
	
	public abstract List<PetDTO>  getAllPetData();
	public abstract PetDTO getPetDataById(Long petId);
	public abstract PetDTO createPetProfile(PetDTO petdto);
	public abstract PetDTO updatePetData(Long petId,PetDTO petdto);
	public abstract DeleteResponse deletePetDate(Long petId);

}
