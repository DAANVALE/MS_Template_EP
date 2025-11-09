package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.BlobStorage.BlobStorageService;
import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.service.CityService;
import com.proyect.mstemplateep.service.TerraceService;
import com.proyect.mstemplateep.service.TerraceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
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
    private BlobStorageService blobStorageService;

    @Autowired
    public TerraceController
            (TerraceService terraceService, TerraceTypeService terraceTypeRepo,
             CityService cityService, BlobStorageService blobStorageService) {
        this.terraceService = terraceService;
        this.terraceTypeService = terraceTypeRepo;
        this.cityService = cityService;
        this.blobStorageService = blobStorageService;
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

    @PostMapping("/Img")
    public ResponseEntity<Terrace> createTerraceImg(@RequestBody Terrace terraceModel, Set<MultipartFile> files) {

        Terrace savedTerrace = createTerrace(terraceModel).getBody();

        if(savedTerrace == null) {
            return ResponseEntity.badRequest().build();
        }

        Set<String> urls = savedTerrace.getURL_Img();

        int count = 0;
        long time = Instant.now().getEpochSecond();

        for(MultipartFile file : files){

            String extension = getExtension(file.getName());

            String imageUrl = "terrace/" + savedTerrace.getId() + "/" + time + "_" + count + extension;
            count++;
            urls.add( blobStorageService.uploadfile(imageUrl, file) );
        }

        savedTerrace.setURL_Img(urls);
        terraceService.saveTerrace(savedTerrace);

        return ResponseEntity.ok(savedTerrace);
    }

    @PostMapping(value = "Img/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Terrace> updateTerraceImg(@PathVariable Integer id, @RequestParam("files") List<MultipartFile> files) {
        Optional<Terrace> terrace = terraceService.findById(id);

        if(!terrace.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        int count = 0;
        long time = Instant.now().getEpochSecond();

        Set<String> urls = terrace.get().getURL_Img();

        for(MultipartFile file : files){

            String extension = getExtension(file.getOriginalFilename());

            String imageUrl = "terrace/" + terrace.get().getId() + "/" + time + "_" + count + extension;
            count++;
            urls.add( blobStorageService.uploadfile(imageUrl, file) );
        }

        terrace.get().setURL_Img(urls);
        terraceService.saveTerrace(terrace.get());

        return ResponseEntity.ok(terrace.get());
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex == -1) return "";
        return filename.substring(lastIndex);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerraceById(@PathVariable Integer id) {
        terraceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // find extras
    @GetMapping("/findByTag")
    public ResponseEntity<List<Terrace>> findByTag(@RequestParam String tag) {
        List<Terrace> terraces = terraceService.findByTag(tag);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/findByAllTags")
    public ResponseEntity<List<Terrace>> findByAllTags(@RequestParam List<String> tags) {
        List<Terrace> terraces = terraceService.findByAllTags(tags);
        return ResponseEntity.ok(terraces);
    }

    @GetMapping("/findByAnyTag")
    public ResponseEntity<List<Terrace>> findByAnyTag(@RequestParam List<String> tags) {
        List<Terrace> terraces = terraceService.findByAnyTag(tags);
        return ResponseEntity.ok(terraces);
    }
}
