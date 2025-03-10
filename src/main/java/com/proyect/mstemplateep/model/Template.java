package com.proyect.mstemplateep.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Template
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idEventType", nullable = false)
    private EventType eventType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idTerraceType", nullable = false)
    private TerraceType terraceType;

    // Data
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
