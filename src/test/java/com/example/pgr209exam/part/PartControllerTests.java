package com.example.pgr209exam.part;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.repository.PartRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
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
public class PartControllerTests {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @AfterEach
    void clearDatabase(@Autowired PartRepository partRepository) {
        partRepository.deleteAll();
    }

    @Test
    @Sql("/sql/part.sql")
    public void getParts_whenExisting_shouldReturn2() {
        String parts = testRestTemplate.getForObject("http://localhost:" + port + "/api/part", String.class);
        Integer totalElements = JsonPath.read(parts, "$.totalElements");

        Assertions.assertEquals(2, totalElements);
    }

    @Test
    public void getParts_whenZero_shouldReturnZero() {
        String parts = testRestTemplate.getForObject("http://localhost:" + port + "/api/part", String.class);
        Integer totalElements = JsonPath.read(parts, "$.totalElements");

        Assertions.assertEquals(0, totalElements);
    }

    @Test
    @Sql("/sql/part.sql")
    public void getPartById_whenExisting_shouldReturnPart() {
        Part part = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertEquals("part 1", part.getPartName());
    }

    @Test
    public void createPart_whenExisting_shouldReturnPart() {
        String partName = "part 3";
        Part part = testRestTemplate.postForObject("http://localhost:" + port + "/api/part", new Part(partName), Part.class);

        Assertions.assertNotNull(part);
        Assertions.assertEquals(partName, part.getPartName());
    }

    @Test
    @Sql("/sql/part.sql")
    public void updatePart_whenUpdated_shouldReturnUpdatedPart() {
        String originalPartName = "part 1";
        String updatedPartName = "part 2";

        Part part = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertEquals(originalPartName, part.getPartName());

        part.setPartName(updatedPartName);
        testRestTemplate.put("http://localhost:" + port + "/api/part/1", part);

        Assertions.assertEquals(updatedPartName, part.getPartName());
    }

    @Test
    @Sql("/sql/part.sql")
    public void deletePartById_whenDeleted_shouldNotFail() {
        Part part = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertEquals("part 1", part.getPartName());

        testRestTemplate.delete("http://localhost:" + port + "/api/part/1");
        Part partAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertNull(partAfterDeleting);
    }
}
