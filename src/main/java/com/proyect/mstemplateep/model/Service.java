package com.proyect.mstemplateep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "service", uniqueConstraints = {@UniqueConstraint(columnNames = "idService_DB")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idServiceType", nullable = false)
    private ServiceType serviceType;

    // ID Service BD
    @Column(name = "idService_DB", nullable = false)
    private Integer idService_DB;

    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Images Repository
    private String URL_Img;

    // Data
    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "place", nullable = false)
    private String place;
}
