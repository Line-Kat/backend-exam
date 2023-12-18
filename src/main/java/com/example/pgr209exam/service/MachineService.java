package com.example.pgr209exam.service;

import com.example.pgr209exam.exception.ResourceNotFoundException;
import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Page<Machine> getMachines(Pageable pageable) {
        return machineRepository.findAll(pageable);
    }

    public Machine getMachineById(Long id) { return machineRepository.findById(id).orElse(null); }

    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public Machine updateMachine(Long id, Machine machineName) {

        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No machine with id: " + id));

        machine.setMachineName(machineName.getMachineName());
        return machineRepository.save(machine);
    }
    public void deleteMachineById(Long id) {
        machineRepository.deleteById(id);
    }
}
