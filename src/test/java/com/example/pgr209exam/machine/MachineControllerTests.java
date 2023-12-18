package com.example.pgr209exam.machine;

import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.repository.MachineRepository;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MachineControllerTests {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @AfterEach
    void clearDatabase(@Autowired MachineRepository machineRepository) {
        machineRepository.deleteAll();
    }
    @Test
    @Sql("/sql/machine.sql")
    public void getMachines_whenExisting_shouldReturn2() {
        String machines = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine", String.class);
        Integer totalElements = JsonPath.read(machines, "$.totalElements");

        Assertions.assertEquals(2, totalElements);
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
        Machine machine2 = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/2", Machine.class);

        Assertions.assertEquals("sewing machine", machine.getMachineName());
        Assertions.assertEquals("blender", machine2.getMachineName());
    }

    @Test
    public void createMachine_whenExisting_shouldReturnMachine() {
        String machineName = "hair dryer";

        Machine machine = testRestTemplate.postForObject("http://localhost:" + port + "/api/machine", new Machine(machineName), Machine.class);

        Assertions.assertNotNull(machine);
        Assertions.assertEquals(machineName, machine.getMachineName());
    }

    @Test
    @Sql("/sql/machine.sql")
    public void updateMachine_whenUpdated_shouldReturnUpdatedMachine() {
        String originalMachineName = "sewing machine";
        String updatedMachineName = "Mix master";

        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);
        Assertions.assertEquals(originalMachineName, machine.getMachineName());

        machine.setMachineName(updatedMachineName);
        testRestTemplate.put("http://localhost:" + port + "/api/machine/1", machine);

        Assertions.assertEquals(updatedMachineName, machine.getMachineName());

    }

    @Test
    public void deleteMachineById_whenDeleted_shouldNotFail() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);

        testRestTemplate.delete("http://localhost:" + port + "/api/machine/1", machine);
        Machine machineAfterDeleting = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);

        Assertions.assertNull(machineAfterDeleting);
    }
    @Test
    @Sql("/sql/machine.sql")
    public void addSubassembly_newSubassemblyIsAddedToMachine() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);
        Assertions.assertEquals(0, machine.getSubassemblies().size());

        Subassembly newSubassembly = new Subassembly("subassemblyName");
        List<Subassembly> subassemblies = machine.getSubassemblies();
        subassemblies.add(newSubassembly);
        machine.setSubassemblies(subassemblies);
        testRestTemplate.put("http://localhost:" + port + "/api/machine/1/add-subassembly", machine);

        Assertions.assertEquals(1, machine.getSubassemblies().size());
    }

    @Test
    @Sql("/sql/machine.sql")
    public void deleteSubassembly_whenDeleted_shouldNotFail() {
        Machine machine = testRestTemplate.getForObject("http://localhost:" + port + "/api/machine/1", Machine.class);
        List<Subassembly> subassemblies = machine.getSubassemblies();
        subassemblies.add(new Subassembly("newSubassembly"));
        subassemblies.add(new Subassembly("anotherSubassembly"));

        Assertions.assertEquals(2, machine.getSubassemblies().size());

        subassemblies.remove(0);

        machine.setSubassemblies(subassemblies);
        System.out.println("Subassembly size: " + machine.getSubassemblies().size());
        testRestTemplate.put("http://localhost:" + port + "/api/machine/1/delete-subassembly", machine);

        Assertions.assertEquals(1, machine.getSubassemblies().size());
    }
}
