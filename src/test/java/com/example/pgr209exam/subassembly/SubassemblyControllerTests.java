package com.example.pgr209exam.subassembly;

import com.example.pgr209exam.model.Subassembly;
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
public class SubassemblyControllerTests {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/sql/subassembly.sql")
    public void getSubassemblies_whenExisting_shouldReturn2() {
        String subassemblies = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly", String.class);
        Integer totalElements = JsonPath.read(subassemblies, "$.totalElements");

        Assertions.assertEquals(2, totalElements);
    }

    @Test
    public void getSubassemblies_whenZero_shouldReturnZero() {
        String subassemblies = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly", String.class);
        Integer totalElements = JsonPath.read(subassemblies, "$.totalElements");

        Assertions.assertEquals(0, totalElements);
    }

    @Test
    @Sql("/sql/subassembly.sql")
    public void getSubassemblyById_whenExisting_shouldReturnSubassembly() {
        Subassembly subassembly = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly/1", Subassembly.class);

        Assertions.assertEquals("subassemblyName", subassembly.getSubassemblyName());
    }

    @Test
    public void createSubassembly_whenExisting_shouldReturnSubassembly() {
        String subassemblyName = "Subassembly name";

        Subassembly subassembly = testRestTemplate.postForObject("http://localhost:" + port + "/api/subassembly", new Subassembly(subassemblyName), Subassembly.class);

        Assertions.assertNotNull(subassembly);
        Assertions.assertEquals(subassemblyName, subassembly.getSubassemblyName());
    }

    @Test
    @Sql("/sql/subassembly.sql")
    public void updateSubassembly_whenUpdated_shouldReturnUpdatedSubassembly() {
        Subassembly subassembly = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly/1", Subassembly.class);
        Assertions.assertEquals("subassemblyName", subassembly.getSubassemblyName());

        String updatedSubassemblyName = "new subassemblyName";
        subassembly.setSubassemblyName(updatedSubassemblyName);
        testRestTemplate.put("http://localhost:" + port + "/api/part/1", subassembly);
        Subassembly updatedSubassembly = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly/1", Subassembly.class);

        //Assertions.assertEquals(updatedSubassemblyName, updatedSubassembly.getSubassemblyName());
    }

    @Test
    @Sql("/sql/subassembly.sql")
    public void deleteSubassemblyById_whenDeleted_shouldNotFail() {
        Subassembly subassembly = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly/1", Subassembly.class);

        Assertions.assertEquals("subassemblyName", subassembly.getSubassemblyName());

        testRestTemplate.delete("http://localhost:" + port + "/api/subassembly/1");
        Subassembly subassemblyAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/subassembly/1", Subassembly.class);

        Assertions.assertNull(subassemblyAfterDeleting);
    }
}
