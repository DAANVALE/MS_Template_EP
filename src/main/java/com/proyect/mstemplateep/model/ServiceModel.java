package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "service", uniqueConstraints = {@UniqueConstraint(columnNames = "idService_DB")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceModel
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "service_servicetype", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "service_id"), // Clave de Service
            inverseJoinColumns = @JoinColumn(name = "service_type_id") // Clave de ServiceType
    )
    @JsonProperty("idServiceType")
    private List<ServiceType> serviceType;

    // ID Service BD
    @JsonProperty("idService_DB")
    @Column(name = "idService_DB", nullable = false)
    private Integer idService_DB;

    @JsonProperty("idAsociate_DB")
    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Images Repository
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
