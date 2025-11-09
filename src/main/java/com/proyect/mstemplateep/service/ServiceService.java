package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private final ServiceRepo serviceRepo;

    @Autowired
    public ServiceService(ServiceRepo serviceRepository) {
        this.serviceRepo = serviceRepository;
    }

    public Page<ServiceModel> getAllServices(Integer page, Integer size){
        return serviceRepo.findAll(PageRequest.of(page, size));
    }

    public Page<ServiceModel> findByServiceType(Integer page, Integer size, ServiceType serviceType){
        return serviceRepo.findByServiceType(serviceType, PageRequest.of(page, size));
    }

    public Optional<ServiceModel> findById(Integer id){
        return serviceRepo.findById(id);
    }

    public ServiceModel saveServiceModel(ServiceModel serviceModel){
        return serviceRepo.save(serviceModel);
    }

    public void deleteById(Integer id) {

        // TODO: Also it must to delete the data from
        if (serviceRepo.existsById(id)) {
            serviceRepo.deleteById(id);
            if (serviceRepo.existsById(id)) {
                throw new RuntimeException("Error al eliminar el servicio");
            }
        } else {
            throw new ResourceNotFoundException("Service not found with id " + id);
        }
    }

    public List<ServiceModel> findByTag(String tag) {
        return serviceRepo.findByTag(tag);
    }

    public List<ServiceModel> findByAllTags(List<String> tags) {
        return serviceRepo.findByAllTags(tags, tags.size());
    }

    public List<ServiceModel> findByAnyTag(List<String> tags) {
        return serviceRepo.findByAnyTag(tags);
    }
}
