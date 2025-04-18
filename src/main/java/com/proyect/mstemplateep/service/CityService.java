package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private final CityRepo cityRepo;

    @Autowired
    public CityService(CityRepo cityRepository) {
        this.cityRepo = cityRepository;
    }

    public List<CityModel> getAllCities(){
        return cityRepo.findByKilled((byte) 0);
    }

    public Optional<CityModel> getCityById(Integer id){
        return cityRepo.findById(id);
    }

    public CityModel saveCity(CityModel city){
        return cityRepo.save(city);
    }
}
