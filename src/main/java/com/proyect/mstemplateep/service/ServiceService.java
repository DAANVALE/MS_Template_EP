package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<ServiceModel> getAllServices(int page, int size){
        return serviceRepo.findAll(PageRequest.of(page, size));
    }

    public Page<ServiceModel> findByServiceType(int page, int size, ServiceType service){
        return serviceRepo.findByServiceType(service, PageRequest.of(page, size));
    }

    public Optional<ServiceModel> findById(Integer id){
        return serviceRepo.findById(id);
    }

    public ServiceModel saveServiceModel(ServiceModel serviceModel){
        return serviceRepo.save(serviceModel);
    }

    public void deleteServiceModel(ServiceModel serviceModel){
        serviceRepo.delete(serviceModel);
    }
}
