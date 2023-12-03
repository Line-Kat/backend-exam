package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Transactional
public class DatabaseTests {
    @Autowired
    private AddressRepository addressRepository;

    protected void addAddresses(final Address... addresses) {
        addressRepository.saveAll(Arrays.asList(addresses));
    }
}
