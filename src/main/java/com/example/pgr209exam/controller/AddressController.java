package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAddresses() {
        return addressService.getAddresses();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.createAddress(address);
    }

    @PutMapping
    public Address updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
    }
}
