package com.visiplus.graines.repository;

import com.visiplus.graines.business.Recette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetteRepository extends JpaRepository<Recette, Long> {
    
    // 4.6 Donnant les recettes triées sur le nombre de types de graine
    @Query("SELECT r FROM Recette r LEFT JOIN r.typesDeGraine t " +
           "GROUP BY r ORDER BY COUNT(t) DESC")
    List<Recette> findRecettesTrieesParNombreTypesDeGraine();
}
