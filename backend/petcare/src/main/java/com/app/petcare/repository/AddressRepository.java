package com.app.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.petcare.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

}
