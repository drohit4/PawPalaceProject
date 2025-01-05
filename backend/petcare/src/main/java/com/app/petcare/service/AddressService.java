package com.app.petcare.service;

import java.util.List;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.AddressDTO;

public interface AddressService {

	public List<AddressDTO> retriveAllAddress();
	public AddressDTO retriveAddressById(Long addressId);
	public AddressDTO createAddress(AddressDTO addressDTO);
	public AddressDTO updatedAddress(Long addressId,AddressDTO adddressDTO);
	public DeleteResponse deleteAddressById(Long addressId);
}
