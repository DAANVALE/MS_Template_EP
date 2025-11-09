package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.ServiceModel;
import com.proyect.mstemplateep.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceModel,Integer>{
    Optional<ServiceModel> findById(Integer id);
    Page<ServiceModel> findByServiceType(ServiceType serviceType, Pageable pageable);

    @Query("SELECT s FROM ServiceModel s JOIN s.tags t WHERE t = :tag")
    List<ServiceModel> findByTag(@Param("tag") String tag);

    // Buscar por MÚLTIPLES tags (que tenga TODOS los tags)
    @Query("SELECT s FROM ServiceModel s JOIN s.tags t WHERE t IN :tags GROUP BY s HAVING COUNT(t) = :tagCount")
    List<ServiceModel> findByAllTags(@Param("tags") List<String> tags, @Param("tagCount") long tagCount);

    // Buscar por AL MENOS UN tag de la lista
    @Query("SELECT DISTINCT s FROM ServiceModel s JOIN s.tags t WHERE t IN :tags")
    List<ServiceModel> findByAnyTag(@Param("tags") List<String> tags);

    // Método automático de Spring Data
    List<ServiceModel> findByTagsContaining(String tag);
}
