package com.visiplus.graines.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recette {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "L'intitulé de la recette ne doit pas être laissé vide")
    private String intitule;
    
    @ManyToMany
    @JoinTable(
        name = "recette_type_de_graine",
        joinColumns = @JoinColumn(name = "recette_id"),
        inverseJoinColumns = @JoinColumn(name = "type_de_graine_id")
    )
    @ToString.Exclude
    @Builder.Default
    private List<TypeDeGraine> typesDeGraine = new ArrayList<>();
}
