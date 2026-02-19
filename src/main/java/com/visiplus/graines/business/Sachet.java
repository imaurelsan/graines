package com.visiplus.graines.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sachet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double prix;
    
    private Integer nombreDeGraines;
    
    @ManyToOne
    @JoinColumn(name = "type_de_graine_id")
    private TypeDeGraine typeDeGraine;
    
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;
    
    @OneToMany(mappedBy = "sachet", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<LigneDeCommande> lignesDeCommande = new ArrayList<>();
}
