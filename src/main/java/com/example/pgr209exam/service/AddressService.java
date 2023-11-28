package com.example.pgr209exam.service;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {

        return addressRepository.findById(id).orElse(null);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

}
