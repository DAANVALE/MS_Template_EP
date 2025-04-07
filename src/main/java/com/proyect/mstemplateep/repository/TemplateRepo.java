package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.EventType;
import com.proyect.mstemplateep.model.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepo extends JpaRepository<Template,Integer>  {
    Page<Template> findByEventType(EventType eventType, Pageable pageable);
    Optional<Template> findById(Integer id);
}
