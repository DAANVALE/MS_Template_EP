package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.service.TerraceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terrace-types")
public class TerraceTypeController {

    @Autowired
    private final TerraceTypeService terraceTypeService;

    @Autowired
    public TerraceTypeController(TerraceTypeService terraceTypeService) {
        this.terraceTypeService = terraceTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TerraceType>> getAllTerraceTypes() {
        List<TerraceType> terraceTypes = terraceTypeService.getAllTerraceTypes();
        return ResponseEntity.ok(terraceTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerraceType> getTerraceTypeById(@PathVariable Integer id) {
        Optional<TerraceType> terraceType = terraceTypeService.getTerraceTypeById(id);
        return terraceType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TerraceType> createService(@RequestBody TerraceType terraceType) {
        try {
            TerraceType savedService = terraceTypeService.saveTerraceType(terraceType);
            return ResponseEntity.ok(savedService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
