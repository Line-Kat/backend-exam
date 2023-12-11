package com.example.pgr209exam.machine;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.repository.AddressRepository;
import com.example.pgr209exam.repository.MachineRepository;
import com.example.pgr209exam.service.AddressService;
import com.example.pgr209exam.service.MachineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MachineServiceTests {

    @MockBean
    private MachineRepository machineRepository;

    @Autowired
    private MachineService machineService;
    @Test
    public void getMachineById_whenExisting_shouldReturnMachine() {
        Machine machine = new Machine("Machine1");
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        Machine actualMachine = machineService.getMachineById(1L);

        Assertions.assertEquals(machine, actualMachine);
    }

    @Test
    public void createMachine_addingNewMachine_shouldReturnMachine() {
        Machine machine = new Machine("Machine2");
        when(machineRepository.save(machine)).thenReturn(machine);

        Machine returnedMachine = machineService.createMachine(machine);

        Assertions.assertNotNull(returnedMachine);
        Assertions.assertEquals("Machine2", returnedMachine.getMachineName());
    }


    @Test
    public void updateMachine_updateExistingMachine_shouldReturnUpdatedMachine() {
        Machine machine = new Machine("Machine 1");
        when(machineRepository.save(machine)).thenReturn(machine);

        Machine firstMachine = machineService.createMachine(machine);

        Assertions.assertEquals("Machine 1", firstMachine.getMachineName());

        machine.setMachineName("Machine 2");
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine updatedMachine = machineService.updateMachine(machine);

        Assertions.assertEquals("Machine 2", updatedMachine.getMachineName());
    }

    @Test
    public void deleteMachine_deleteExistingMachine_shouldNotFail() {
        Machine machine = new Machine("Machine");
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine createdMachine = machineService.createMachine(machine);

        Assertions.assertEquals("Machine", createdMachine.getMachineName());

        machineService.deleteMachineById(1L);

        Machine deletedMachine = machineService.getMachineById(1L);

        Assertions.assertNull(deletedMachine);
    }

}
