package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.service.CityService;
import com.proyect.mstemplateep.service.TerraceService;
import com.proyect.mstemplateep.service.TerraceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/terrace")
public class TerraceController {

    @Autowired
    private final TerraceService terraceService;

    @Autowired
    private final TerraceTypeService terraceTypeService;

    @Autowired
    private final CityService cityService;

    @Autowired
    public TerraceController(TerraceService terraceService, TerraceTypeService terraceTypeRepo, CityService cityService) {
        this.terraceService = terraceService;
        this.terraceTypeService = terraceTypeRepo;
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<Terrace>> getAllTerraces(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        PageRequest pageable = PageRequest.of(page, size);
        Page<Terrace> terraces = terraceService.getAllTerraces(pageable);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/TerraceType/{terraceTypeId}")
    public ResponseEntity<Page<Terrace>> getAllTerraceTypes(
            @PathVariable Integer terraceTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        TerraceType terraceType = terraceTypeService.getTerraceTypeById(terraceTypeId).orElseThrow();

        PageRequest pageable = PageRequest.of(page, size);
        Page<Terrace> terraces = terraceService.findByTerraceType(pageable,terraceType);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terrace> getTerraceById(@PathVariable Integer id) {
        Optional<Terrace> terrace = terraceService.findById(id);
        return terrace.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Terrace> createTerrace(@RequestBody Terrace terrace) {
        // Mapea los IDs de los TerraceTypes a objetos TerraceType
        Set<TerraceType> terraceTypes = terrace.getTerraceType().stream()
                .map(terraceType -> terraceTypeService.getTerraceTypeById(terraceType.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("TerraceType {"+ terraceType.getId() + "} not found")))
                .collect(Collectors.toSet());  // Usamos collect(toSet()) para evitar duplicados

        if (terrace.getCityModel() != null){
            CityModel cityModel = cityService.getCityById(terrace.getCityModel().getId())
                    .orElseThrow(() -> new RuntimeException("CityModel not found"));
            terrace.setCityModel(cityModel);
        }

        // Asocia los TerraceTypes al Terrace
        terrace.setTerraceType(terraceTypes);

        // Guarda el Terrace
        Terrace savedTerrace = terraceService.saveTerrace(terrace);

        return ResponseEntity.ok(savedTerrace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerraceById(@PathVariable Integer id) {
        terraceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
