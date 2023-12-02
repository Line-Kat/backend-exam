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

    //unidirectional
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "machine_id",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<Machine> machines = new ArrayList<>();

    //unidirectional
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "machine_id")
    private List<Subassembly> subassemblies = new ArrayList<>();

}
