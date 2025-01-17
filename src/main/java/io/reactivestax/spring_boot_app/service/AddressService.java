package io.reactivestax.spring_boot_app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.dto.AddressDTO;
import io.reactivestax.spring_boot_app.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<AddressDTO> findAll() {
        return addressRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<AddressDTO> findById(Long id) {
        return addressRepository.findById(id).map(this::convertToDTO);
    }

    public AddressDTO save(AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        return convertToDTO(addressRepository.save(address));
    }

    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }
}