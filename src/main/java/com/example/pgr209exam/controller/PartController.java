package com.example.pgr209exam.controller;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/part")
public class PartController {
    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public Page<Part> getParts() {
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return partService.getParts(pageable);
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Long id) {
        return partService.getPartById(id);
    }

    @PostMapping
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @RequestBody Part partName) {
        Part updatedPart = partService.updatePart(id, partName);
        if (updatedPart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPart);
    }

    @DeleteMapping("/{id}")
    public void deletePartById(@PathVariable Long id) {
        partService.deletePartById(id);
    }
}
