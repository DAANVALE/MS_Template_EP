package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.*;
import com.proyect.mstemplateep.service.*;
import jdk.jfr.Event;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private final TemplateService templateService;

    @Autowired
    private final EventTypeService eventTypeService;

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Autowired
    private final TerraceTypeService terraceTypeService;

    @Autowired
    private final CityService cityService;

    @Autowired
    public TemplateController
            (TemplateService templateService, EventTypeService eventTypeService, ServiceTypeService serviceTypeService,
             TerraceTypeService terraceTypeService, CityService cityService) {
        this.templateService = templateService;
        this.eventTypeService = eventTypeService;
        this.serviceTypeService = serviceTypeService;
        this.terraceTypeService = terraceTypeService;
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<Template>> getTemplates(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Template> template = templateService.getAllTemplates(page,size);
        return ResponseEntity.ok(template);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Template> getserviceTypeById(@PathVariable Integer id) {
        Optional<Template> template = templateService.findById(id);
        return template.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("EventType/{eventTypeId}")
    public ResponseEntity<Page<Template>> getTemplateByEventType(
            @PathVariable Integer eventTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        EventType eventType = eventTypeService.getEventTypeById(eventTypeId).orElseThrow();

        Page<Template> templates = templateService.findByEventType(page, size, eventType);
        return ResponseEntity.ok(templates);
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        try {

            Set<ServiceType> serviceTypes = template.getServiceType().stream()
                    .map(serviceType -> serviceTypeService.getServiceTypeById(serviceType.getId())
                            .orElseThrow(() -> new RuntimeException("ServiceType not found")))
                    .collect(Collectors.toSet());
            template.setServiceType(serviceTypes);

            if (template.getEventType() != null) {
                EventType eventType = eventTypeService.getEventTypeById(template.getEventType().getId())
                        .orElseThrow(() -> new RuntimeException("EventType not found"));
                template.setEventType(eventType);
            }

            // Asigna el TerraceType si existe
            if (template.getTerraceType() != null) {
                TerraceType terraceType = terraceTypeService.getTerraceTypeById(template.getTerraceType().getId())
                        .orElseThrow(() -> new RuntimeException("TerraceType not found"));
                template.setTerraceType(terraceType);
            }

            if (template.getCityModel() != null){
                CityModel cityModel = cityService.getCityById(template.getCityModel().getId())
                        .orElseThrow(() -> new RuntimeException("CityModel not found"));
                template.setCityModel(cityModel);
            }

            Template savedTemplate = templateService.saveTemplate(template);
            return ResponseEntity.ok(savedTemplate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
