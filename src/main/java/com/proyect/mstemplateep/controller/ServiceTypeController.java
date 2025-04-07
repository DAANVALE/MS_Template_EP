package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController {

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllserviceTypes() {
        List<ServiceType> serviceTypes = serviceTypeService.getAllServiceTypes();
        return ResponseEntity.ok(serviceTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getserviceTypeById(@PathVariable Integer id) {
        Optional<ServiceType> serviceType = serviceTypeService.getServiceTypeById(id);
        return serviceType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceType> createService(@RequestBody ServiceType serviceType) {
        ServiceType savedService = serviceTypeService.saveServiceType(serviceType);
        return ResponseEntity.ok(savedService);
    }
}
