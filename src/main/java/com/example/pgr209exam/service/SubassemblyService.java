package com.example.pgr209exam.service;

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

    public Subassembly getSubassemblyById(Long id) {

        return subassemblyRepository.findById(id).orElse(null);
    }

    public Subassembly createSubassembly(Subassembly subassembly) {
        return subassemblyRepository.save(subassembly);
    }

    public Subassembly updateSubassembly(Subassembly subassembly) {
        return subassemblyRepository.save(subassembly);
    }

    public void deleteSubassemblyById(Long id) {
        subassemblyRepository.deleteById(id);
    }
}
