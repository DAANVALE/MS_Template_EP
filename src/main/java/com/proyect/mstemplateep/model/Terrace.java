package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("terraceType")
    @ManyToMany(targetEntity = TerraceType.class)
    private Set<TerraceType> terraceType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cityModel", nullable = false)
    @JsonProperty("cityModel")
    private CityModel cityModel;

    // ID Service BD
    @JsonProperty("idTerrace_DB")
    @Column(name = "idTerrace_DB", nullable = false)
    private Integer idTerrace_DB;

    @JsonProperty("idAsociate_DB")
    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Image Repository
    @ElementCollection
    @CollectionTable(
            name = "terrace_images",
            joinColumns = @JoinColumn(name = "id") // Este debe coincidir con el nombre real de tu PK
    )
    @Column(name = "image_url")
    @JsonProperty("URL_Img")
    private Set<String> URL_Img;

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

    @ElementCollection
    @CollectionTable(
            name = "terrace_tags",
            joinColumns = @JoinColumn(name = "terrace_id")
    )

    @JsonProperty("tags")
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    public Set<TerraceType> getTerraceType() {
        return terraceType;
    }

    public void setTerraceType(Set<TerraceType> terraceType) {
        this.terraceType = terraceType;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public void setURL_Img(Set<String> URL_Img) {
        this.URL_Img = URL_Img;
    }

    public Set<String> getURL_Img() {
        return URL_Img;
    }

}
