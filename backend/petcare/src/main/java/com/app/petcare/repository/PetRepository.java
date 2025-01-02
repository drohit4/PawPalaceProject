package com.app.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.petcare.models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>{

}
