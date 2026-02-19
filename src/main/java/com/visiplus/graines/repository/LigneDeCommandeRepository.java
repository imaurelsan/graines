package com.visiplus.graines.repository;

import com.visiplus.graines.business.LigneDeCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneDeCommandeRepository extends JpaRepository<LigneDeCommande, Long> {
}
