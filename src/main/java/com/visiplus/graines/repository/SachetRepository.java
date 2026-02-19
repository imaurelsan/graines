package com.visiplus.graines.repository;

import com.visiplus.graines.business.Sachet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachetRepository extends JpaRepository<Sachet, Long> {
    
    // 4.2 Donnant les sachets qui n'ont jamais été commandés
    @Query("SELECT s FROM Sachet s WHERE s.lignesDeCommande IS EMPTY")
    List<Sachet> findSachetsJamaisCommandes();
    
    // 4.4 Donnant les sachets triés sur la quantité commandée décroissante
    @Query("SELECT s FROM Sachet s LEFT JOIN s.lignesDeCommande ldc " +
           "GROUP BY s ORDER BY COALESCE(SUM(ldc.quantite), 0) DESC")
    List<Sachet> findSachetsTriesParQuantiteCommandee();
}
