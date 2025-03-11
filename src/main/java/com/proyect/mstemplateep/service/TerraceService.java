package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.Template;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import com.proyect.mstemplateep.repository.TerraceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TerraceService {

    @Autowired
    private final TerraceRepo terraceRepo;

    @Autowired
    public TerraceService(TerraceRepo terraceRepository) {
        this.terraceRepo = terraceRepository;
    }

    public Page<Terrace> getAllTerraces(Integer page, Integer size){
        return terraceRepo.findAll(PageRequest.of(page, size));
    }

    public Page<Terrace> findByTerraceType(Integer page, Integer size, TerraceType terraceType){
        return terraceRepo.findByTerraceType(terraceType, PageRequest.of(page, size));
    }

    public Optional<Terrace> findById(Integer id){
        return terraceRepo.findById(id);
    }

    public Terrace saveTerrace(Terrace terrace){
        return terraceRepo.save(terrace);
    }

    public void deleteById(Integer id){
        if (terraceRepo.existsById(id)) {
            terraceRepo.deleteById(id);
            if (terraceRepo.existsById(id)) {
                throw new RuntimeException("Error al eliminar el servicio");
            }
        } else {
            throw new ResourceNotFoundException("Service not found with id " + id);
        }
    }
}
