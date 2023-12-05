package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public class AddressServiceTests extends DatabaseTests{

    @Autowired
    private AddressService addressService;

    @BeforeEach
    public void setUp(){
        addAddresses(new Address("Dronninggata"));
    }

    @Test
    public void getAddresses_whenExisting_shouldReturn1() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Address> addressPage = addressService.getAddresses(pageable);
        long numberOfAddresses = addressPage.getTotalElements();

        Assertions.assertEquals(1, numberOfAddresses);
    }

    @Test
    public void getAddressById_whenExisting_shouldReturnAddress() {
       Address address = addressService.getAddressById(1L);

        Assertions.assertEquals("Dronninggata", address.getAddressName());
    }

    @Test
    public void createAddress_addingNewAddress_shouldReturnAddress() {
        Address address = new Address("Prinsessealleen");
        Address returnedAddress = addressService.createAddress(address);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Address> addressPage = addressService.getAddresses(pageable);
        List<Address> addresses = addressPage.getContent();

        Assertions.assertNotNull(returnedAddress);
        Assertions.assertEquals("Prinsessealleen", returnedAddress.getAddressName());
        Assertions.assertEquals(2, addresses.size());
    }

    @Test
    public void updateAddress_updateExistingAddress_shouldReturnUpdatedAddress() {
        Address address = addressService.getAddressById(1L);
        address.setAddressName("Rådhusgata");
        Address updatedAddress = addressService.updateAddress(address);

        Assertions.assertEquals("Rådhusgata", updatedAddress.getAddressName());
    }

    @Test
    public void deleteAddress_deleteExistingAddress_shouldReturnZero() {
        addressService.deleteAddressById(1L);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Address> addressPage = addressService.getAddresses(pageable);
        Long numberOfAddresses = addressPage.getTotalElements();

        Assertions.assertEquals(0, numberOfAddresses);
    }

}
