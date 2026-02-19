package com.visiplus.graines.repository;

import com.visiplus.graines.business.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    
    // 4.1 Listant toutes les commandes envoyées entre deux dates données en paramètre
    @Query("SELECT c FROM Commande c WHERE c.dateDeCommande BETWEEN :dateDebut AND :dateFin")
    List<Commande> findCommandesEntreDeuxDates(
        @Param("dateDebut") LocalDate dateDebut, 
        @Param("dateFin") LocalDate dateFin
    );
    
    // 4.9 Donnant les commandes triées sur le montant total décroissant
    @Query("SELECT c FROM Commande c LEFT JOIN c.lignesDeCommande ldc LEFT JOIN ldc.sachet s " +
           "GROUP BY c ORDER BY COALESCE(SUM(ldc.quantite * s.prix), 0) DESC")
    List<Commande> findCommandesTrieesParMontantTotal();
    
    // 4.10 Donnant le nombre de commandes par mois
    @Query("SELECT FUNCTION('YEAR', c.dateDeCommande) as annee, " +
           "FUNCTION('MONTH', c.dateDeCommande) as mois, " +
           "COUNT(c) as nombre " +
           "FROM Commande c " +
           "GROUP BY FUNCTION('YEAR', c.dateDeCommande), FUNCTION('MONTH', c.dateDeCommande) " +
           "ORDER BY annee, mois")
    List<Object[]> countCommandesParMois();
}
