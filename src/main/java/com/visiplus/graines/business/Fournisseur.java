package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fournisseur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    @Pattern(regexp = "^0[67].*", message = "Le numéro de téléphone doit débuter par 06 ou 07")
    private String telephone;
    
    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Sachet> sachets = new ArrayList<>();
}
