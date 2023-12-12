package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine")
public class MachineController {
    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public Page<Machine> getMachines() {
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return machineService.getMachines(pageable);
    }

    @GetMapping("/{id}")
    public Machine getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id);
    }

    @PostMapping
    public Machine createMachine(@RequestBody Machine machine) {
        return machineService.createMachine(machine);
    }

    @PutMapping
    public Machine updateMachine(@RequestBody Machine machine) {
        return machineService.updateMachine(machine);
    }

    @DeleteMapping("/{id}")
    public void deleteMachineById(@PathVariable Long id) {
        machineService.deleteMachineById(id);
    }
}
