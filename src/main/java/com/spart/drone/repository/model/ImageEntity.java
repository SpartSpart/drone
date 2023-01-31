package com.spart.drone.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name = "image",schema = "public")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image")
    private byte[] image;
}
