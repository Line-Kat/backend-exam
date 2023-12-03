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
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part-sequence-generator")
    @SequenceGenerator(name = "part-sequence-generator", sequenceName = "part-sequence", allocationSize = 1)
    @Column(name = "part_id")
    private Long partId = 0L;

    @Column(name = "part_name")
    private String partName;
}
