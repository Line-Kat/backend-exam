package com.example.pgr209exam.repository;

import com.example.pgr209exam.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {}
