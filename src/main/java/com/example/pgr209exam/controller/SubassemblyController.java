package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subassembly")
public class SubassemblyController {
    private final SubassemblyService subassemblyService;

    @Autowired
    public SubassemblyController(SubassemblyService subassemblyService) {
        this.subassemblyService = subassemblyService;
    }

    @GetMapping
    public List<Subassembly> getSubassembly() {
        return subassemblyService.getSubassemblies();
    }

    @GetMapping("/{id}")
    public Subassembly getSubassemblyById(@PathVariable Long id) {
        return subassemblyService.getSubassemblyById(id);
    }

    @PostMapping
    public Subassembly createSubassembly(@RequestBody Subassembly subassembly) {
        return subassemblyService.createSubassembly(subassembly);
    }

    @PutMapping
    public Subassembly updateSubassembly(@RequestBody Subassembly subassembly) {
        return subassemblyService.updateSubassembly(subassembly);
    }

    @DeleteMapping("/{id}")
    public void deleteSubassemblyById(@PathVariable Long id) {
        subassemblyService.deleteSubassemblyById(id);
    }
}
