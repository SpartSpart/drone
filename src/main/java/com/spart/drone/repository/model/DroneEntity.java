package com.spart.drone.repository.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drone",schema = "public")
@Getter
@Setter
@ToString(exclude = "medicationEntitySet")
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model", nullable = false)
    private ModelEntity model;

    @Column(name = "weight")
    private int weight;

    @Column(name = "battery_capacity")
    private int batteryСapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state", nullable = false)
    private StateEntity state;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "drone_medication",
            joinColumns = { @JoinColumn(name = "drone_id") },
            inverseJoinColumns = { @JoinColumn(name = "medication_id") }
    )
    List<MedicationEntity> medicationEntities;

    public void addMedication(MedicationEntity medicationEntity){
        medicationEntities.add(medicationEntity);
    }
}
