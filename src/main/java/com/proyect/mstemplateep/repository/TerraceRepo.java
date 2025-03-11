package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerraceRepo extends JpaRepository<Terrace,Integer>{

    Optional<Terrace> findById(Integer id);
    Page<Terrace> findByTerraceType(TerraceType serviceType, Pageable pageable);
}
