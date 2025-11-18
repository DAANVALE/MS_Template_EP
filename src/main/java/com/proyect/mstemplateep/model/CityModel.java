package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "city_model")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityModel
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("kind")
    @Column(name = "kind", length = 50)
    private String kind;

    @JsonProperty("killed")
    @Column(name = "killed")
    private Byte killed = 0;

    public void setKilled(Byte killed) {
        this.killed = killed;
    }

    public Integer getId() {
        return id;
    }

    // PK foreign key
    @ManyToMany(targetEntity = ServiceModel.class,
            mappedBy = "cityModel",
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ServiceModel> serviceModels;

    @OneToMany(mappedBy = "cityModel", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Evita loops infinitos en JSON
    @JsonIgnore
    private Set<Template> templates;

    @OneToMany(mappedBy = "cityModel", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Evita loops infinitos en JSON
    @JsonIgnore
    private Set<Terrace> terrace;
}
