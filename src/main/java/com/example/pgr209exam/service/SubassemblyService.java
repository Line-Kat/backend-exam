package com.example.pgr209exam.service;

import com.example.pgr209exam.exception.ResourceNotFoundException;
import com.example.pgr209exam.model.Machine;

import com.example.pgr209exam.model.Customer;
import com.example.pgr209exam.model.Order;
import com.example.pgr209exam.model.Part;

import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.repository.SubassemblyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubassemblyService {
    private final SubassemblyRepository subassemblyRepository;
    @Autowired
    public SubassemblyService(SubassemblyRepository subassemblyRepository) {
        this.subassemblyRepository = subassemblyRepository;
    }

    public Page<Subassembly> getSubassemblies(Pageable pageable) {
        return subassemblyRepository.findAll(pageable);
    }

    public Subassembly getSubassemblyById(Long id) { return subassemblyRepository.findById(id).orElse(null); }

    public Subassembly createSubassembly(Subassembly subassembly) {
        return subassemblyRepository.save(subassembly);
    }

    public Subassembly updateSubassembly(Subassembly subassembly) {
        return subassemblyRepository.save(subassembly);
    }

    public Subassembly updateSubassembly(Long id, Subassembly subassemblyName) {

        Subassembly subassembly = subassemblyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subassembly with id: " + id));

        subassembly.setSubassemblyName(subassemblyName.getSubassemblyName());
        return subassemblyRepository.save(subassembly);
    }

    public void deleteSubassemblyById(Long id) {
        subassemblyRepository.deleteById(id);
    }

    public Subassembly addPart(Long id, Part part) {
        Subassembly subassembly = getSubassemblyById(id);
        List<Part> parts = subassembly.getParts();
        parts.add(part);
        subassembly.setParts(parts);

        return subassemblyRepository.save(subassembly);
    }

    public void deletePart(Long id, Part part) {
        Subassembly subassembly = getSubassemblyById(id);
        List<Part> parts = subassembly.getParts();
        parts.remove(part);
        subassembly.setParts(parts);

        subassemblyRepository.save(subassembly);
    }


}
