package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.TerraceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerraceTypeRepo extends JpaRepository<TerraceType,Integer>  {
    List<TerraceType> findByKilled(Byte killed);
    Optional<TerraceType> findById(Integer id);
}
