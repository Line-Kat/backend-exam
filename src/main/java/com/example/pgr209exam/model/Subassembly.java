package com.example.pgr209exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
