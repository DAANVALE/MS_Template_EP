package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.EventType;
import com.proyect.mstemplateep.model.Template;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.repository.EventTypeRepo;
import com.proyect.mstemplateep.repository.TemplateRepo;
import com.proyect.mstemplateep.repository.ServiceTypeRepo;
import com.proyect.mstemplateep.repository.TemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    public TemplateService(TemplateRepo templateRepo) {
        this.templateRepo = templateRepo;
    }

    public Page<Template> getAllTemplates(Integer page, Integer size){
        return templateRepo.findAll(PageRequest.of(page, size));
    }

    public Page<Template> findByEventType(Integer page, Integer size, EventType eventType){
        return templateRepo.findByEventType(eventType, PageRequest.of(page, size));
    }

    public Optional<Template> findById(Integer id){
        return templateRepo.findById(id);
    }

    public Template saveTemplate(Template Template){
        return templateRepo.save(Template);
    }

    public void deleteById(Integer id){
        if (templateRepo.existsById(id)) {
            templateRepo.deleteById(id);
            if (templateRepo.existsById(id)) {
                throw new RuntimeException("Error al eliminar el servicio");
            }
        } else {
            throw new ResourceNotFoundException("Service not found with id " + id);
        }
    }
}
