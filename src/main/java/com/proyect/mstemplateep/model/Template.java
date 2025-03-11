package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "template_servicetype", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "template_id"), // Clave de Service
            inverseJoinColumns = @JoinColumn(name = "servicetype_id") // Clave de ServiceType
    )
    @JsonProperty("idServiceType")
    private List<ServiceType> serviceType;

    // Data
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
