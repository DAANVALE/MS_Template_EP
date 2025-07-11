package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceModel,Integer>{

    Optional<ServiceModel> findById(Integer id);
    Page<ServiceModel> findByServiceType(ServiceType serviceType, Pageable pageable);
}
