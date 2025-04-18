package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.CityModel;
import com.proyect.mstemplateep.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<CityModel,Integer>  {
    List<CityModel> findByKilled(Byte killed);
    Optional<CityModel> findById(Integer id);
}
