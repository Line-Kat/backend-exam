package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part")
public class PartController {
    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> getParts() {
        return partService.getParts();
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Long id) {
        return partService.getPartById(id);
    }

    @PostMapping
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }

    @PutMapping
    public Part updatePart(@RequestBody Part part) {
        return partService.updatePart(part);
    }

    @DeleteMapping("/{id}")
    public void deletePartById(@PathVariable Long id) {
        partService.deletePartById(id);
    }
}
