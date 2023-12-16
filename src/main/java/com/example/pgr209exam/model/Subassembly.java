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
public class Subassembly {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subassembly-sequence-generator")
    @SequenceGenerator(name = "subassembly-sequence-generator", sequenceName = "subassembly-sequence", allocationSize = 1)
    @Column(name = "subassembly_id")
    private Long subassemblyId = 0L;

    @Column(name = "subassembly_name")
    private String subassemblyName;

    public Subassembly(String subassemblyName) {
        this.subassemblyName = subassemblyName;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "subassembly_part",
            joinColumns = @JoinColumn(name = "subassembly_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> parts = new ArrayList<>();
}
