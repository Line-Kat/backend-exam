package com.example.pgr209exam.machine;

import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.repository.MachineRepository;
import com.example.pgr209exam.service.MachineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MachineServiceTests {
    @MockBean
    private MachineRepository machineRepository;

    @Autowired
    private MachineService machineService;

    @Test
     public void testGetMachines() {
        Page<Machine> mockedPage = mock(Page.class);
        when(machineRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

        Page<Machine> result = machineService.getMachines(PageRequest.of(0, 5));
        verify(machineRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(mockedPage, result);
    }

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
        Long machineId = 1L;
        String existingMachineName = "Sewing machine";
        String newMachineName = "Blender";

        Machine existingMachine = new Machine();
        existingMachine.setMachineId(machineId);
        existingMachine.setMachineName(existingMachineName);

        when(machineRepository.findById(machineId)).thenReturn(Optional.of(existingMachine));
        when(machineRepository.save(any(Machine.class))).thenAnswer(i -> i.getArgument(0));

        Machine updatedFieldName = new Machine();
        updatedFieldName.setMachineName(newMachineName);

        Machine updatedMachine = machineService.updateMachine(machineId, updatedFieldName);

        assertNotNull(updatedMachine);
        assertEquals(newMachineName, updatedMachine.getMachineName());
    }

    @Test
    public void deleteMachine_deleteExistingMachine_shouldNotFail() {
        String machineName = "Popcorn maker";

        Machine machine = new Machine(machineName);
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine createdMachine = machineService.createMachine(machine);

        Assertions.assertEquals(machine, createdMachine);

        machineService.deleteMachineById(1L);
        Machine deletedMachine = machineService.getMachineById(1L);

        Assertions.assertNull(deletedMachine);
    }
}
