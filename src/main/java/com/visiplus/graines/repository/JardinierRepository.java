package com.visiplus.graines.repository;

import com.visiplus.graines.business.Jardinier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JardinierRepository extends JpaRepository<Jardinier, Long> {
    
    // 4.3 Listant tous les jardiniers ayant déjà commandé du basilic
    @Query("SELECT DISTINCT j FROM Jardinier j " +
           "JOIN j.commandes c " +
           "JOIN c.lignesDeCommande ldc " +
           "JOIN ldc.sachet s " +
           "JOIN s.typeDeGraine t " +
           "WHERE LOWER(t.nom) = LOWER(:nomGraine)")
    List<Jardinier> findJardiniersAyantCommandeGraine(@Param("nomGraine") String nomGraine);
    
    // 4.7 Listant les jardiniers triés sur le nombre de commandes décroissant
    @Query("SELECT j FROM Jardinier j LEFT JOIN j.commandes c " +
           "GROUP BY j ORDER BY COUNT(c) DESC")
    List<Jardinier> findJardiniersTriesParNombreCommandes();
    
    // 4.8 Listant les jardiniers de plus de 60 ans
    @Query("SELECT j FROM Jardinier j WHERE " +
           "FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', j.dateDeNaissance) > 60")
    List<Jardinier> findJardiniersDesPlusDe60Ans();
}
