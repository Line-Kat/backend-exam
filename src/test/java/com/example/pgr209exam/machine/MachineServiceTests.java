package com.example.pgr209exam.machine;

import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.repository.MachineRepository;
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
        Machine machine = new Machine("Mix master");
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        Machine actualMachine = machineService.getMachineById(1L);

        Assertions.assertEquals(machine, actualMachine);
    }

    @Test
    public void createMachine_addingNewMachine_shouldReturnMachine() {
        String machineName = "Air fryer";

        Machine machine = new Machine(machineName);
        when(machineRepository.save(machine)).thenReturn(machine);

        Machine returnedMachine = machineService.createMachine(machine);

        Assertions.assertNotNull(returnedMachine);
        Assertions.assertEquals(machineName, returnedMachine.getMachineName());
    }

    @Test
    public void updateMachine_updateExistingMachine_shouldReturnUpdatedMachine() {
        String originalName = "Sewing machine";
        String updatedName = "Blender";
        
        Machine machine = new Machine(originalName);
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine originalMachine = machineService.createMachine(machine);

        Assertions.assertEquals(originalName, originalMachine.getMachineName());

        originalMachine.setMachineName(updatedName);
        when(machineRepository.save(originalMachine)).thenReturn(originalMachine);
        Machine updatedMachine = machineService.updateMachine(originalMachine);

        Assertions.assertEquals(updatedName, updatedMachine.getMachineName());
    }

    @Test
    public void deleteMachine_deleteExistingMachine_shouldNotFail() {
        String machineName = "Popcorn maker";

        Machine machine = new Machine(machineName);
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine createdMachine = machineService.createMachine(machine);

        Assertions.assertEquals(machineName, createdMachine.getMachineName());

        machineService.deleteMachineById(1L);
        Machine deletedMachine = machineService.getMachineById(1L);

        Assertions.assertNull(deletedMachine);
    }
}
