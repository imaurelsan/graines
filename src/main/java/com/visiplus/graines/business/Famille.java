package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Famille {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String intitule;
    
    @Size(min = 6, max = 6, message = "La couleur doit contenir exactement 6 caractères")
    private String couleur;
    
    @OneToMany(mappedBy = "famille", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<TypeDeGraine> typesDeGraine = new ArrayList<>();
}
