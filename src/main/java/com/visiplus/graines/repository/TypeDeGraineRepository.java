package com.visiplus.graines.repository;

import com.visiplus.graines.business.TypeDeGraine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDeGraineRepository extends JpaRepository<TypeDeGraine, Long> {
    
    // 4.5 Donnant les types de graine qu'il est possible de planter aujourd'hui
    @Query("SELECT DISTINCT t FROM TypeDeGraine t WHERE " +
           "FUNCTION('WEEK', CURRENT_DATE) BETWEEN t.semaineDebut AND t.semaineFin")
    List<TypeDeGraine> findTypesPlantablesAujourdhui();
}
