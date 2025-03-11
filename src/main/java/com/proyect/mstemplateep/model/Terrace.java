package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "terrace", uniqueConstraints = {@UniqueConstraint(columnNames = "idTerrace_DB")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Terrace
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "terrace_terracetype", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "terrace_id"), // Clave de Service
            inverseJoinColumns = @JoinColumn(name = "terracetype_id") // Clave de ServiceType
    )
    private List<TerraceType> terraceType;

    // ID Service BD
    @JsonProperty("idTerrace_DB")
    @Column(name = "idTerrace_DB", nullable = false, unique = true)
    private Integer idTerrace_DB;

    @JsonProperty("idAsociate_DB")
    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Image Repository
    @JsonProperty("URL_Img")
    @Column(name = "URL_Img", nullable = false)
    private String URL_Img;

    // Data
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("price")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @JsonProperty("place")
    @Column(name = "place", nullable = false)
    private String place;
}
