package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city-types")
public class CityController {

    @Autowired
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityModel>> getAllCitys() {
        List<CityModel> citys = cityService.getAllCities();
        return ResponseEntity.ok(citys);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityModel> getCityById(@PathVariable Integer id) {
        Optional<CityModel> city = cityService.getCityById(id);
        return city.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CityModel> createService(@RequestBody CityModel city) {
        CityModel savedService = cityService.saveCity(city);
        return ResponseEntity.ok(savedService);
    }
}
