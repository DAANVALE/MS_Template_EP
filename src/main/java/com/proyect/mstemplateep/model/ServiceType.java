package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "service_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceType
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

    @ManyToMany(targetEntity = ServiceModel.class,
            mappedBy = "serviceType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Set<ServiceModel> serviceModels;

    @ManyToMany(targetEntity = Template.class,
            mappedBy = "serviceType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Set<Template> templates;

    public Integer getId() {
        return id;
    }

    public void setKilled(Byte killed) {
        this.killed = killed;
    }

//    public Set<ServiceModel> getServiceModels() {
//        return serviceModels;
//    }
//
//    public void setServiceModels(Set<ServiceModel> serviceModels) {
//        this.serviceModels = serviceModels;
//    }
}
