package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Template
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JsonProperty("idEventType")
    @JoinColumn(name = "idEventType", nullable = false)
    private EventType eventType;

    @JsonProperty("idTerraceType")
    @ManyToOne(optional = false)
    @JoinColumn(name = "idTerraceType", nullable = false)
    private TerraceType terraceType;

    @JsonProperty("serviceType")
    @ManyToMany(targetEntity = ServiceType.class,
            cascade = CascadeType.ALL )
    private Set<ServiceType> serviceType;

    // Data
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
