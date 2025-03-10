package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.ServiceType;
import com.proyect.mstemplateep.repository.ServiceTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    private final ServiceTypeRepo serviceTypeRepo;

    @Autowired
    public ServiceTypeService(ServiceTypeRepo serviceTypeRepository) {
        this.serviceTypeRepo = serviceTypeRepository;
    }

    public List<ServiceType> getAllServiceTypes(){
        return serviceTypeRepo.findByKilled((byte) 0);
    }

    public Optional<ServiceType> getServiceTypeById(Integer id){
        return serviceTypeRepo.findById(id);
    }

    public ServiceType saveServiceType(ServiceType serviceType){
        return serviceTypeRepo.save(serviceType);
    }
}
