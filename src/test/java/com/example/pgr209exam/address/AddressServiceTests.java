package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.repository.AddressRepository;
import com.example.pgr209exam.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

//source for pagination: https://www.javaguides.net/2022/02/spring-data-jpa-pagination-and-sorting.html

@SpringBootTest
public class AddressServiceTests extends DatabaseTests{

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;



    //TEST DOESN'T RUN BECAUSE 'addressPage' IS NULL. Why doesn't addressService.getAddresses(pageable) return anything?
    //Is the implementation of pagination wrong!?
    @Test
    public void getAddresses_whenExisting_shouldReturn1() {

        List<Address> addresses = List.of(new Address("Dronninggata"), new Address("Prinsessealleen"));
        when(addressRepository.findAll()).thenReturn(addresses);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Address> addressPage = addressService.getAddresses(pageable);
        long numberOfAddresses = addressPage.getTotalElements();

        Assertions.assertEquals(2, numberOfAddresses);
    }

    @Test
    public void getAddressById_whenExisting_shouldReturnAddress() {
        Address address = new Address("Dronninggata");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        var actualAddress = addressService.getAddressById(1L);

        Assertions.assertEquals(address, actualAddress);
    }

    @Test
    public void createAddress_addingNewAddress_shouldReturnAddress() {
        Address address = new Address("Prinsessealleen");
        when(addressRepository.save(address)).thenReturn(address);

        var returnedAddress = addressService.createAddress(address);

        Assertions.assertNotNull(returnedAddress);
        Assertions.assertEquals("Prinsessealleen", returnedAddress.getAddressName());
    }

    @Test
    public void updateAddress_updateExistingAddress_shouldReturnUpdatedAddress() {
        Address address = new Address("Blåklokkestien");
        when(addressRepository.save(address)).thenReturn(address);

        var firstAddress = addressService.createAddress(address);
        Assertions.assertEquals("Blåklokkestien", firstAddress.getAddressName());

        address.setAddressName("Rådhusgata");
        when(addressRepository.save(address)).thenReturn(new Address("Rådhusgata"));
        var updatedAddress = addressService.updateAddress(address);

        Assertions.assertEquals("Rådhusgata", updatedAddress.getAddressName());
    }

    //End-to-end-test
    @Test
    public void deleteAddress_deleteExistingAddress_shouldReturnZero() {
        addAddresses(new Address("Dronninggata"));
        addressService.deleteAddressById(1L);

        Address address = addressService.getAddressById(1L);

        Assertions.assertNull(address);
    }
}
