package com.example.pgr209exam.service;

import com.example.pgr209exam.exception.ResourceNotFoundException;
import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Page<Address> getAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Address getAddressById(Long id) { return addressRepository.findById(id).orElse(null); }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressName) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No address with id: " + id));
        address.setAddressName(addressName.getAddressName());
        return addressRepository.save(address);
    }

    public void deleteAddressById(Long id) { addressRepository.deleteById(id); }
}
