package com.visiplus.graines.service;

import com.visiplus.graines.business.TypeDeGraine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TypeDeGraineService {
    
    /**
     * Récupère un type de graine par son id
     */
    Optional<TypeDeGraine> getTypeDeGraineById(Long id);
    
    /**
     * Récupère une page de types de graine
     */
    Page<TypeDeGraine> getAllTypesDeGraine(Pageable pageable);
    
    /**
     * Ajoute un nouveau type de graine
     */
    TypeDeGraine createTypeDeGraine(TypeDeGraine typeDeGraine);
    
    /**
     * Modifie un type de graine existant
     */
    TypeDeGraine updateTypeDeGraine(Long id, TypeDeGraine typeDeGraine);
    
    /**
     * Supprime un type de graine
     */
    void deleteTypeDeGraine(Long id);
}
