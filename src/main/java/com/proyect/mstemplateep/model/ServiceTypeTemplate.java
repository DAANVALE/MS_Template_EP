package com.proyect.mstemplateep.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idServiceType", nullable = false)
    private ServiceType serviceType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idTemplateType", nullable = false)
    private Template Template;

    private Byte isDelete;
}
