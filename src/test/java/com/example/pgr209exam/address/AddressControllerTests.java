package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class AddressControllerTests {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/sql/address.sql")
    public void getAddresses_whenExisting_shouldReturn1() {
        Address[] addresses = testRestTemplate.getForObject("http://localhost:" + port + "/api/address", Address[].class);

        Assertions.assertEquals(1, addresses.length);
        Assertions.assertEquals("name", addresses[0].getAddressName());
    }

    @Test
    public void getAddresses_whenZero_shouldReturnZero() {
        Address[] addresses = testRestTemplate.getForObject("http://localhost:" + port + "/api/address", Address[].class);

        Assertions.assertEquals(0, addresses.length);
    }

    @Test
    @Sql("/sql/address.sql")
    public void getAddressById_whenExisting_shouldReturnAddress() {
        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);

        Assertions.assertEquals("name", address.getAddressName());
    }

    @Test
    public void createAddress_whenExisting_shouldReturnAddress() {
        Address address = testRestTemplate.postForObject("http://localhost:" + port + "/api/address", new Address("Storgata"), Address.class);

        Assertions.assertNotNull(address);
        Assertions.assertEquals("Storgata", address.getAddressName());
    }

    @Test
    @Sql("/sql/address.sql")
    public void updateAddress_whenUpdated_shouldReturnUpdatedAddress() {
        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);
        Assertions.assertEquals("name", address.getAddressName());

        address.setAddressName("Blomsterbakken");
        testRestTemplate.put("http://localhost:" + port + "/api/address/1", address);

        Assertions.assertEquals("Blomsterbakken", address.getAddressName());
    }

    @Test
    @Sql("/sql/address.sql")
    public void deleteAddressById_whenDeleted_shouldReturnNull() {
        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);
        testRestTemplate.delete(String.valueOf(address));


        Assertions.assertNull(address);
    }
}
