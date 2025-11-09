package com.proyect.mstemplateep.repository;

import com.proyect.mstemplateep.model.Terrace;
import com.proyect.mstemplateep.model.TerraceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerraceRepo extends JpaRepository<Terrace,Integer>{
    Optional<Terrace> findById(Integer id);
    Page<Terrace> findByTerraceType(TerraceType serviceType, Pageable pageable);

    @Query("SELECT t FROM Terrace t JOIN t.tags tag WHERE tag = :tag")
    List<Terrace> findByTag(@Param("tag") String tag);

    // Buscar que tenga TODOS los tags
    @Query("SELECT t FROM Terrace t JOIN t.tags tag WHERE tag IN :tags GROUP BY t HAVING COUNT(tag) = :tagCount")
    List<Terrace> findByAllTags(@Param("tags") List<String> tags, @Param("tagCount") long tagCount);

    // Buscar que tenga AL MENOS UN tag
    @Query("SELECT DISTINCT t FROM Terrace t JOIN t.tags tag WHERE tag IN :tags")
    List<Terrace> findByAnyTag(@Param("tags") List<String> tags);
}
