package com.example.pgr209exam.service;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartService {
    private final PartRepository partRepository;

    @Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Page<Part> getParts(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    public Part getPartById(Long id) { return partRepository.findById(id).orElse(null); }

    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public Part updatePart(Part part) {
        return partRepository.save(part);
    }

    public void deletePartById(Long id) {
        partRepository.deleteById(id);
    }
}
