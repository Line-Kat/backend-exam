package com.example.pgr209exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine-sequence-generator")
    @SequenceGenerator(name = "machine-sequence-generator", sequenceName = "machine-sequence", allocationSize = 1)
    @Column(name = "machine_id")
    private Long machineId = 0L;

    @Column(name = "machine_name")
    private String machineName;

    public Machine(String machineName) {
        this.machineName = machineName;
    }

    //unidirectional
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subassembly_id")
    private List<Subassembly> subassemblies = new ArrayList<>();
}