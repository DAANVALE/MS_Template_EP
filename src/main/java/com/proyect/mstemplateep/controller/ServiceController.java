package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.repository.ServiceTypeRepo;
import com.proyect.mstemplateep.service.ServiceService;
import com.proyect.mstemplateep.service.ServiceTypeService;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private final ServiceService serviceService;

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ServiceController(ServiceService serviceService, ServiceTypeService serviceTypeRepo) {
        this.serviceService = serviceService;
        this.serviceTypeService = serviceTypeRepo;
    }

    @GetMapping
    public ResponseEntity<Page<ServiceModel>> getAllServices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        Page<ServiceModel> services = serviceService.getAllServices(page, size);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/ServiceType/{serviceTypeId}")
    public ResponseEntity<Page<ServiceModel>> getAllServiceTypes(
            @PathVariable Integer serviceTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        ServiceType serviceType = serviceTypeService.getServiceTypeById(serviceTypeId).orElseThrow();

        Page<ServiceModel> services = serviceService.findByServiceType(page, size,serviceType);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable Integer id) {
        Optional<ServiceModel> service = serviceService.findById(id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceModel> createService(@RequestBody ServiceModel serviceModel) {
        try {
            // Mapea los IDs de los ServiceTypes a objetos ServiceType
            Set<ServiceType> serviceTypes = serviceModel.getServiceType().stream()
                    .map(serviceType -> serviceTypeService.getServiceTypeById(serviceType.getId())  // Aquí usamos el ID directamente
                            .orElseThrow(() -> new RuntimeException("ServiceType not found")))
                    .collect(Collectors.toSet());  // Usamos collect(toSet()) para evitar duplicados

            // Asocia los ServiceTypes al ServiceModel
            serviceModel.setServiceType(serviceTypes);

            // Guarda el ServiceModel
            ServiceModel savedService = serviceService.saveServiceModel(serviceModel);

            return ResponseEntity.ok(savedService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable Integer id) {
        serviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
