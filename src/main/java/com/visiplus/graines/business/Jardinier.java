package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jardinier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    private String prenom;
    
    @PastOrPresent(message = "La date de naissance doit être dans le passé")
    private LocalDate dateDeNaissance;
    
    @OneToMany(mappedBy = "jardinier", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Commande> commandes = new ArrayList<>();
}
