package com.spart.drone.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name = "model",schema = "public")
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",unique = true, nullable = false)
    private String name;

    @Column(name = "weight_limit", nullable = false)
    private Integer weightLimit;
}
