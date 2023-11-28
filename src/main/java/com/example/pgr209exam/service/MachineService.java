package com.example.pgr209exam.service;

import com.example.pgr209exam.model.Address;
import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> getMachines() {
        return machineRepository.findAll();
    }

    public Machine getMachineById(Long id) {

        return machineRepository.findById(id).orElse(null);
    }

    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public Machine updateMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public void deleteMachineById(Long id) {
        machineRepository.deleteById(id);
    }
}
