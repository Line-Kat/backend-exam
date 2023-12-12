package com.example.pgr209exam.part;

import com.example.pgr209exam.model.Part;
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
public class PartControllerTests {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/sql/part.sql")
    public void getParts_whenExisting_shouldReturn1() {
        String parts = testRestTemplate.getForObject("http://localhost:" + port + "/api/part", String.class);
        Integer totalElements = JsonPath.read(parts, "$.totalElements");

        Assertions.assertEquals(1, totalElements);
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

        Assertions.assertEquals("name", part.getPartName());
    }

    @Test
    public void createPart_whenExisting_shouldReturnPart() {
        Part part = testRestTemplate.postForObject("http://localhost:" + port + "/api/part", new Part("Part"), Part.class);

        Assertions.assertNotNull(part);
        Assertions.assertEquals("Part", part.getPartName());
    }

    @Test
    @Sql("/sql/part.sql")
    public void updatePart_whenUpdated_shouldReturnUpdatedPart() {
        Part part = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);
        Assertions.assertEquals("name", part.getPartName());

        part.setPartName("Part");
        testRestTemplate.put("http://localhost:" + port + "/api/part/1", part);

        Assertions.assertEquals("Part", part.getPartName());
    }

    @Test
    @Sql("/sql/part.sql")
    public void deletePartById_whenDeleted_shouldNotFail() {
        Part part = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertEquals("name", part.getPartName());

        testRestTemplate.delete("http://localhost:" + port + "/api/part/1");
        Part partAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/part/1", Part.class);

        Assertions.assertNull(partAfterDeleting);
    }

}
