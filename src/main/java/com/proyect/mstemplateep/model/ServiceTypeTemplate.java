package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "ServiceType_Template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceTypeTemplate
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JsonProperty("idServiceType")
    @JoinColumn(name = "idServiceType", nullable = false)
    private ServiceType serviceType;

    @ManyToOne(optional = false)
    @JsonProperty("idTemplateType")
    @JoinColumn(name = "idTemplateType", nullable = false)
    private Template Template;
}
