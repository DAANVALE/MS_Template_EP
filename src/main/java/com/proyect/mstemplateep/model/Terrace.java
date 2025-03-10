package com.proyect.mstemplateep.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "terrace", uniqueConstraints = {@UniqueConstraint(columnNames = "idTerrace_DB")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Terrace
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idTerraceType", nullable = false)
    private TerraceType terraceType;

    // ID Service BD
    @Column(name = "idTerrace_DB", nullable = false, unique = true)
    private Integer idTerrace_DB;

    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Image Repository
    @Column(name = "URL_Img", nullable = false)
    private String URL_Img;

    // Data
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "place", nullable = false)
    private String place;
}
