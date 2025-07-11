package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonProperty("eventType")
    @JoinColumn(name = "eventType", nullable = false)
    private EventType eventType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "terraceType", nullable = false)
    @JsonProperty("terraceType")
    private TerraceType terraceType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cityModel", nullable = false)
    @JsonProperty("cityModel")
    private CityModel cityModel;

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

    public Set<ServiceType> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Set<ServiceType> serviceType) {
        this.serviceType = serviceType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public TerraceType getTerraceType() {
        return terraceType;
    }

    public void setTerraceType(TerraceType terraceType) {
        this.terraceType = terraceType;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
