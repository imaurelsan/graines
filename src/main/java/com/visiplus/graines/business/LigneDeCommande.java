package com.visiplus.graines.business;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneDeCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantite;
    
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
    
    @ManyToOne
    @JoinColumn(name = "sachet_id")
    private Sachet sachet;
}
