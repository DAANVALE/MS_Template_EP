package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("serviceType")
    @ManyToMany(targetEntity = ServiceType.class)
    private Set<ServiceType> serviceType;

    @JsonProperty("cityModel")
    @ManyToMany(targetEntity = CityModel.class)
    private Set<CityModel> cityModel;

    // ID Service BD
    @JsonProperty("idService_DB")
    @Column(name = "idService_DB", nullable = false)
    private Integer idService_DB;

    @JsonProperty("idAsociate_DB")
    @Column(name = "idAsociate_DB", nullable = false)
    private Integer idAsociate_DB;

    // Images Repository
    @ElementCollection
    @CollectionTable(
            name = "service_images",
            joinColumns = @JoinColumn(name = "id") // Este debe coincidir con la PK de ServiceModel
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

    // Add tags
    @ElementCollection
    @CollectionTable(
            name = "service_tags",
            joinColumns = @JoinColumn(name = "service_id")
    )

    @JsonProperty("tags")
    @Column(name = "tags")
    private List<String> tags = new ArrayList<>();

    public Set<ServiceType> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Set<ServiceType> serviceType) {
        this.serviceType = serviceType;
    }

    public Set<CityModel> getCityModel() {
        return cityModel;
    }

    public void setCityModel(Set<CityModel> cityModel) {
        this.cityModel = cityModel;
    }

    public void setURL_Img(Set<String> URL_Img) {
        this.URL_Img = URL_Img;
    }

    public Set<String> getURL_Img() {
        return URL_Img;
    }

    public Integer getId() {
        return id;
    }
}
