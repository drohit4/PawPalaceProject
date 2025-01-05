package com.app.petcare.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petcare.customeresponse.DeleteResponse;
import com.app.petcare.dto.AddressDTO;
import com.app.petcare.exceptions.ResourceNotFoundException;
import com.app.petcare.models.Address;
import com.app.petcare.repository.AddressRepository;
import com.app.petcare.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private ModelMapper modelMappper;

	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
		this.addressRepository = addressRepository;
		this.modelMappper = modelMapper;
	}

	@Override
	public List<AddressDTO> retriveAllAddress() {
		List<Address> addressList = this.addressRepository.findAll();
		if (addressList.isEmpty())
			throw new ResourceNotFoundException("Address List is Empty");
		return convertAddressToDTOs(addressList);
	}

	@Override
	public AddressDTO retriveAddressById(Long addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address is not found with Given Id : " + addressId));
		return this.modelMappper.map(address, AddressDTO.class);
	}

	@Override
	public AddressDTO createAddress(AddressDTO addressDTO) {
		Address address = this.modelMappper.map(addressDTO, Address.class);
		address = this.addressRepository.save(address);
		return this.modelMappper.map(address, AddressDTO.class);
	}

	@Override
	public AddressDTO updatedAddress(Long addressId, AddressDTO adddressDTO) {
		Address address = this.modelMappper.map(adddressDTO, Address.class);
		Address savedAddress = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address is not found with Given Id : " + addressId));
		return updateAddressData(savedAddress, adddressDTO);
	}

	@Override
	public DeleteResponse deleteAddressById(Long addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address is not found with Given Id : " + addressId));
		this.addressRepository.deleteById(addressId);
		return new DeleteResponse(LocalDateTime.now(),"Address Data successfully remove!!!");
	}

	/*********************************************************************************/
	private List<AddressDTO> convertAddressToDTOs(List<Address> addressList) {
		return addressList.stream().map(item -> this.modelMappper.map(item, AddressDTO.class)).toList();
	}

	private AddressDTO updateAddressData(Address savedAddress, AddressDTO addressDTO) {
		savedAddress.setAddrsLine1(addressDTO.getAddrsLine1());
		savedAddress.setAddrsLine2(addressDTO.getAddrsLine2());
		savedAddress.setCity(addressDTO.getCity());
		savedAddress.setCountry(addressDTO.getCountry());
		savedAddress.setPincode(addressDTO.getPincode());
		return this.modelMappper.map(savedAddress, AddressDTO.class);
	}

}
