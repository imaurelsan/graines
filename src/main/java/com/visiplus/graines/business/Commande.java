package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate dateDeCommande;
    
    @ManyToOne
    @JoinColumn(name = "jardinier_id")
    private Jardinier jardinier;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "La commande doit avoir au moins une ligne de commande")
    @ToString.Exclude
    @Builder.Default
    private List<LigneDeCommande> lignesDeCommande = new ArrayList<>();
    
    public Double getMontantTotal() {
        return lignesDeCommande.stream()
                .mapToDouble(ligne -> ligne.getQuantite() * ligne.getSachet().getPrix())
                .sum();
    }
}
