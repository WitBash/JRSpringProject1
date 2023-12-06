package com.boshko.jrspringproject1.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task", schema = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
}
