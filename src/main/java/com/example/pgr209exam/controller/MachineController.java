package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Machine;
import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<Machine> updateMachine(@PathVariable Long id, @RequestBody Machine machineName) {
        Machine updatedMachine = machineService.updateMachine(id, machineName);
        if (updatedMachine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMachine);
    }

    @DeleteMapping("/{id}")
    public void deleteMachineById(@PathVariable Long id) {
        machineService.deleteMachineById(id);
    }



    @PutMapping("/{id}/add-subassembly")
    public ResponseEntity<Machine> addSubassembly(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        Machine updatedMachine = machineService.addSubassembly(id, subassembly);
        if (updatedMachine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMachine);
    }

    @PutMapping("/{id}/delete-subassembly")
    public ResponseEntity<Machine> deletePart(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        Machine updatedMachine = machineService.deleteSubassembly(id, subassembly);
        if (updatedMachine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMachine);
    }
}
