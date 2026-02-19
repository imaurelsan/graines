package com.visiplus.graines.controller;

import com.visiplus.graines.business.TypeDeGraine;
import com.visiplus.graines.service.TypeDeGraineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/types-de-graine")
@RequiredArgsConstructor
@Tag(name = "Types de Graine", description = "API de gestion des types de graine")
public class TypeDeGraineController {
    
    private final TypeDeGraineService typeDeGraineService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un type de graine par son ID", 
               description = "Retourne un type de graine spécifique basé sur son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Type de graine trouvé"),
        @ApiResponse(responseCode = "404", description = "Type de graine non trouvé")
    })
    public ResponseEntity<TypeDeGraine> getTypeDeGraineById(
            @Parameter(description = "ID du type de graine à récupérer") 
            @PathVariable Long id) {
        return typeDeGraineService.getTypeDeGraineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Récupérer une page de types de graine", 
               description = "Retourne une page paginée de tous les types de graine")
    @ApiResponse(responseCode = "200", description = "Page de types de graine récupérée avec succès")
    public ResponseEntity<Page<TypeDeGraine>> getAllTypesDeGraine(
            @Parameter(description = "Paramètres de pagination (page, size, sort)")
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(typeDeGraineService.getAllTypesDeGraine(pageable));
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau type de graine", 
               description = "Ajoute un nouveau type de graine dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Type de graine créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<TypeDeGraine> createTypeDeGraine(
            @Parameter(description = "Type de graine à créer")
            @Valid @RequestBody TypeDeGraine typeDeGraine) {
        TypeDeGraine created = typeDeGraineService.createTypeDeGraine(typeDeGraine);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un type de graine existant", 
               description = "Met à jour les informations d'un type de graine existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Type de graine modifié avec succès"),
        @ApiResponse(responseCode = "404", description = "Type de graine non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<TypeDeGraine> updateTypeDeGraine(
            @Parameter(description = "ID du type de graine à modifier")
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données du type de graine")
            @Valid @RequestBody TypeDeGraine typeDeGraine) {
        try {
            TypeDeGraine updated = typeDeGraineService.updateTypeDeGraine(id, typeDeGraine);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un type de graine", 
               description = "Supprime un type de graine de la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Type de graine supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Type de graine non trouvé")
    })
    public ResponseEntity<Void> deleteTypeDeGraine(
            @Parameter(description = "ID du type de graine à supprimer")
            @PathVariable Long id) {
        typeDeGraineService.deleteTypeDeGraine(id);
        return ResponseEntity.noContent().build();
    }
}
