package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTypeRepo extends JpaRepository<EventType,Integer>  {

    List<EventType> findByKilled(Byte killed);
    Optional<EventType> findById(Integer id);
}
