package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nom"))
public class TypeDeGraine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String nom;
    
    @Size(min = 40, message = "Les conseils doivent contenir au minimum 40 caractères")
    private String conseils;
    
    @Min(value = 1, message = "La semaine de plantation doit être comprise entre 1 et 52")
    @Max(value = 52, message = "La semaine de plantation doit être comprise entre 1 et 52")
    private Integer semaineDebut;
    
    @Min(value = 1, message = "La semaine de plantation doit être comprise entre 1 et 52")
    @Max(value = 52, message = "La semaine de plantation doit être comprise entre 1 et 52")
    private Integer semaineFin;
    
    @ManyToOne
    @JoinColumn(name = "famille_id", nullable = false)
    @NotNull(message = "Le type de graine doit obligatoirement être associé à une famille")
    private Famille famille;
    
    @OneToMany(mappedBy = "typeDeGraine", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Sachet> sachets = new ArrayList<>();
    
    @ManyToMany(mappedBy = "typesDeGraine")
    @ToString.Exclude
    @Builder.Default
    private List<Recette> recettes = new ArrayList<>();
}
