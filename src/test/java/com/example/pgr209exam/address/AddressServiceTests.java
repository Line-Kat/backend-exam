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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

//source for pagination: https://www.javaguides.net/2022/02/spring-data-jpa-pagination-and-sorting.html

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

            Assertions.assertEquals(mockedPage, result);
    }

    @Test
    public void getAddressById_whenExisting_shouldReturnAddress() {
        Address address = new Address("Dronninggata");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Address actualAddress = addressService.getAddressById(1L);

        Assertions.assertEquals(address, actualAddress);
    }

    @Test
    public void createAddress_addingNewAddress_shouldReturnAddress() {
        Address address = new Address("Prinsessealleen");
        when(addressRepository.save(address)).thenReturn(address);

        Address returnedAddress = addressService.createAddress(address);

        Assertions.assertNotNull(returnedAddress);
        Assertions.assertEquals("Prinsessealleen", returnedAddress.getAddressName());
    }

    @Test
    public void updateAddress_updateExistingAddress_shouldReturnUpdatedAddress() {
        Address address = new Address("Blåklokkestien");
        when(addressRepository.save(address)).thenReturn(address);

        Address firstAddress = addressService.createAddress(address);

        Assertions.assertEquals("Blåklokkestien", firstAddress.getAddressName());

        address.setAddressName("Rådhusgata");
        when(addressRepository.save(address)).thenReturn(new Address("Rådhusgata"));
        Address updatedAddress = addressService.updateAddress(address);

        Assertions.assertEquals("Rådhusgata", updatedAddress.getAddressName());
    }

    @Test
    public void deleteAddress_deleteExistingAddress_shouldReturnZero() {
        Address address = new Address("Svingen");
        when(addressRepository.save(address)).thenReturn(address);
        Address createdAddress = addressService.createAddress(address);

        Assertions.assertEquals("Svingen", createdAddress.getAddressName());

        addressService.deleteAddressById(1L);

        Address deletedAddress = addressService.getAddressById(1L);

        Assertions.assertNull(deletedAddress);
    }
}
