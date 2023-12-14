package com.example.pgr209exam.address;

import com.example.pgr209exam.model.Address;
import com.jayway.jsonpath.JsonPath;
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
    public void getAddresses_whenExisting_shouldReturn2() {
        String addresses = testRestTemplate.getForObject("http://localhost:" + port + "/api/address", String.class);
        Integer totalElements = JsonPath.read(addresses, "$.totalElements");

        Assertions.assertEquals(2, totalElements);
    }

    @Test
    public void getAddresses_whenZero_shouldReturnZero() {
        String addresses = testRestTemplate.getForObject("http://localhost:" + port + "/api/address", String.class);
        Integer totalElements = JsonPath.read(addresses, "$.totalElements");

        Assertions.assertEquals(0, totalElements);
    }

    @Test
    @Sql("/sql/address.sql")
    public void getAddressById_whenExisting_shouldReturnAddress() {
        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);

        Assertions.assertEquals("Solsikkeengen", address.getAddressName());
    }

    @Test
    public void createAddress_whenExisting_shouldReturnAddress() {
        String addressName = "Storgata";

        Address address = testRestTemplate.postForObject("http://localhost:" + port + "/api/address", new Address(addressName), Address.class);

        Assertions.assertNotNull(address);
        Assertions.assertEquals(addressName, address.getAddressName());
    }

    @Test
    @Sql("/sql/address.sql")
    public void updateAddress_whenUpdated_shouldReturnUpdatedAddress() {
        String originalAddressName = "Solsikkeengen";
        String updatedAddressName = "Blomsterbakken";

        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);
        Assertions.assertEquals(originalAddressName, address.getAddressName());

        address.setAddressName(updatedAddressName);
        testRestTemplate.put("http://localhost:" + port + "/api/address/1", address);

        Assertions.assertEquals(updatedAddressName, address.getAddressName());
    }

    @Test
    @Sql("/sql/address.sql")
    public void deleteAddressById_whenDeleted_shouldReturnNull() {
        Address address = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);

        Assertions.assertEquals("Solsikkeengen", address.getAddressName());

        testRestTemplate.delete("http://localhost:" + port + "/api/address/1");
        Address addressAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/address/1", Address.class);

        Assertions.assertNull(addressAfterDeleting);
    }
}
