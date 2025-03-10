package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType,Integer>  {

    List<ServiceType> findByKilled(Byte killed);
    Optional<ServiceType> findById(Integer id);
}
