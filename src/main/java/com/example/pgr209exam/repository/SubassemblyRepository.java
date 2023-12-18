package com.example.pgr209exam.repository;

import com.example.pgr209exam.model.Subassembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SubassemblyRepository extends JpaRepository<Subassembly, Long> {}
