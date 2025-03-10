package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.repository.TerraceTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerraceTypeService {

    @Autowired
    private final TerraceTypeRepo terraceTypeRepo;

    @Autowired
    public TerraceTypeService(TerraceTypeRepo terraceTypeRepository) {
        this.terraceTypeRepo = terraceTypeRepository;
    }

    public List<TerraceType> getAllTerraceTypes(){
        return terraceTypeRepo.findByKilled((byte) 0);
    }

    public Optional<TerraceType> getTerraceTypeById(Integer id){
        return terraceTypeRepo.findById(id);
    }

    public TerraceType saveTerraceType(TerraceType terraceType){
        return terraceTypeRepo.save(terraceType);
    }
}
