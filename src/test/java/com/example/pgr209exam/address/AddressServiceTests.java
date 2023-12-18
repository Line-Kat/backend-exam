package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.repository.AddressRepository;
import com.example.pgr209exam.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class AddressServiceTests{
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Test
    public void getAddresses_whenExisting_shouldReturnPage() {
            Page<Address> mockedPage = mock(Page.class);
            when(addressRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Address> result = addressService.getAddresses(pageable);
            verify(addressRepository, times(1)).findAll(pageable);

            assertEquals(mockedPage, result);
    }

    @Test
    public void getAddressById_whenExisting_shouldReturnAddress() {
        Address address = new Address("Dronninggata");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address actualAddress = addressService.getAddressById(1L);

        assertEquals(address, actualAddress);
    }

    @Test
    public void createAddress_addingNewAddress_shouldReturnAddress() {
        String addressName = "Prinsessealleen";
        Address address = new Address(addressName);
        when(addressRepository.save(address)).thenReturn(address);
        Address returnedAddress = addressService.createAddress(address);

        assertNotNull(returnedAddress);
        assertEquals(address, returnedAddress);
    }

    @Test
    public void updateAddress_updateExistingAddress_shouldReturnUpdatedAddress() {
        Long addressId = 1L;
        String existingAddressName = "Blåklokkestien";
        String newAddressName = "Rådhusgata";

        Address existingAddress = new Address();
        existingAddress.setAddressId(addressId);
        existingAddress.setAddressName(existingAddressName);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address updatedFieldAddress = new Address();
        updatedFieldAddress.setAddressName(newAddressName);

        Address updatedAddress = addressService.updateAddress(addressId, updatedFieldAddress);

        assertNotNull(updatedAddress);
        assertEquals(newAddressName, updatedAddress.getAddressName());
    }

    @Test
    public void deleteAddress_deleteExistingAddress_shouldReturnZero() {
        String addressName = "Svingen";
        Address address = new Address(addressName);
        when(addressRepository.save(address)).thenReturn(address);
        Address createdAddress = addressService.createAddress(address);

        assertEquals(address, createdAddress);

        addressService.deleteAddressById(1L);
        Address deletedAddress = addressService.getAddressById(1L);

        Assertions.assertNull(deletedAddress);
    }
}
