package com.visiplus.graines.repository;

import com.visiplus.graines.business.Famille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilleRepository extends JpaRepository<Famille, Long> {
}
