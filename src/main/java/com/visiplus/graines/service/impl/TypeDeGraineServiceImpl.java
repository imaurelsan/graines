package com.visiplus.graines.service.impl;

import com.visiplus.graines.business.TypeDeGraine;
import com.visiplus.graines.repository.TypeDeGraineRepository;
import com.visiplus.graines.service.TypeDeGraineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TypeDeGraineServiceImpl implements TypeDeGraineService {
    
    private final TypeDeGraineRepository typeDeGraineRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDeGraine> getTypeDeGraineById(Long id) {
        log.debug("Récupération du type de graine avec l'id: {}", id);
        return typeDeGraineRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TypeDeGraine> getAllTypesDeGraine(Pageable pageable) {
        log.debug("Récupération de la page {} des types de graine", pageable.getPageNumber());
        return typeDeGraineRepository.findAll(pageable);
    }
    
    @Override
    public TypeDeGraine createTypeDeGraine(TypeDeGraine typeDeGraine) {
        log.info("Création d'un nouveau type de graine: {}", typeDeGraine.getNom());
        return typeDeGraineRepository.save(typeDeGraine);
    }
    
    @Override
    public TypeDeGraine updateTypeDeGraine(Long id, TypeDeGraine typeDeGraine) {
        log.info("Modification du type de graine avec l'id: {}", id);
        return typeDeGraineRepository.findById(id)
                .map(existant -> {
                    existant.setNom(typeDeGraine.getNom());
                    existant.setConseils(typeDeGraine.getConseils());
                    existant.setSemaineDebut(typeDeGraine.getSemaineDebut());
                    existant.setSemaineFin(typeDeGraine.getSemaineFin());
                    existant.setFamille(typeDeGraine.getFamille());
                    return typeDeGraineRepository.save(existant);
                })
                .orElseThrow(() -> new RuntimeException("Type de graine non trouvé avec l'id: " + id));
    }
    
    @Override
    public void deleteTypeDeGraine(Long id) {
        log.info("Suppression du type de graine avec l'id: {}", id);
        typeDeGraineRepository.deleteById(id);
    }
}
