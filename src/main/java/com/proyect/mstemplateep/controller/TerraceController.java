package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.service.TerraceService;
import com.proyect.mstemplateep.service.TerraceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public TerraceController(TerraceService terraceService, TerraceTypeService terraceTypeRepo) {
        this.terraceService = terraceService;
        this.terraceTypeService = terraceTypeRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Terrace>> getAllTerraces(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        Page<Terrace> terraces = terraceService.getAllTerraces(page, size);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/TerraceType/{terraceTypeId}")
    public ResponseEntity<Page<Terrace>> getAllTerraceTypes(
            @PathVariable Integer terraceTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        TerraceType terraceType = terraceTypeService.getTerraceTypeById(terraceTypeId).orElseThrow();

        Page<Terrace> terraces = terraceService.findByTerraceType(page, size,terraceType);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terrace> getTerraceById(@PathVariable Integer id) {
        Optional<Terrace> terrace = terraceService.findById(id);
        return terrace.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Terrace> createTerrace(@RequestBody Terrace terrace) {
        try {
            // Mapea los IDs de los TerraceTypes a objetos TerraceType
            Set<TerraceType> terraceTypes = terrace.getTerraceType().stream()
                    .map(terraceType -> terraceTypeService.getTerraceTypeById(terraceType.getId())  // Aquí usamos el ID directamente
                            .orElseThrow(() -> new RuntimeException("TerraceType not found")))
                    .collect(Collectors.toSet());  // Usamos collect(toSet()) para evitar duplicados

            // Asocia los TerraceTypes al Terrace
            terrace.setTerraceType(terraceTypes);

            // Guarda el Terrace
            Terrace savedTerrace = terraceService.saveTerrace(terrace);

            return ResponseEntity.ok(savedTerrace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerraceById(@PathVariable Integer id) {
        terraceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
