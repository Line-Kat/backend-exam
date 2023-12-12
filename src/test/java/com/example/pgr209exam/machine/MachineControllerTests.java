package com.example.pgr209exam.machine;

import com.example.pgr209exam.model.Machine;
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
public class MachineControllerTests {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    @Sql("/sql/machine.sql")
    public void getMachines_whenExisting_shouldReturn1() {
        String machines = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine", String.class);
        Integer totalElements = JsonPath.read(machines, "$.totalElements");

        Assertions.assertEquals(1, totalElements);
    }


    @Test
    public void getMachines_whenZero_shouldReturnZero() {
        String machines = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine", String.class);
        Integer totalElements = JsonPath.read(machines, "$.totalElements");

        Assertions.assertEquals(0, totalElements);
    }

    @Test
    @Sql("/sql/machine.sql")
    public void getMachineById_whenExisting_shouldReturnMachine() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);

        Assertions.assertEquals("name", machine.getMachineName());
    }

    @Test
    public void createMachine_whenExisting_shouldReturnMachine() {
        Machine machine = testRestTemplate.postForObject("http://localhost:" + port + "/api/machine", new Machine("Machine"), Machine.class);

        Assertions.assertNotNull(machine);
        Assertions.assertEquals("Machine", machine.getMachineName());
    }

    @Test
    @Sql("/sql/machine.sql")
    public void updateMachine_whenUpdated_shouldReturnUpdatedMachine() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);
        Assertions.assertEquals("name", machine.getMachineName());

        machine.setMachineName("Machine");
        testRestTemplate.put("http://localhost:" + port + "/api/machine/1", machine);

        Assertions.assertEquals("Machine", machine.getMachineName());
    }

    @Test
    @Sql("/sql/machine.sql")
    public void deleteMachineById_whenDeleted_shouldNotFail() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);

        Assertions.assertEquals("name", machine.getMachineName());

        testRestTemplate.delete("http://localhost:" + port + "/api/machine/1");
        Machine machineAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);

        Assertions.assertNull(machineAfterDeleting);
    }
}
