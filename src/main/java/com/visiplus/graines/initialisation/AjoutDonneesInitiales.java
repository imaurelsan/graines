package com.visiplus.graines.initialisation;

import com.visiplus.graines.business.*;
import com.visiplus.graines.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AjoutDonneesInitiales implements CommandLineRunner {
    
    private final FamilleRepository familleRepository;
    private final TypeDeGraineRepository typeDeGraineRepository;
    private final FournisseurRepository fournisseurRepository;
    private final SachetRepository sachetRepository;
    private final JardinierRepository jardinierRepository;
    private final CommandeRepository commandeRepository;
    private final RecetteRepository recetteRepository;
    
    @Override
    public void run(String... args) throws Exception {
        ajouterDonneesInitiales();
        afficherStatistiques();
    }
    
    private void ajouterDonneesInitiales() {
        log.info("=== Ajout des données initiales ===");
        
        // 5.1 Deux familles
        Famille aromatiques = Famille.builder()
                .intitule("les aromatiques")
                .couleur("00FF00")
                .build();
        familleRepository.save(aromatiques);
        
        Famille fruits = Famille.builder()
                .intitule("les fruits")
                .couleur("FF0000")
                .build();
        familleRepository.save(fruits);
        
        log.info("Familles créées: {} et {}", aromatiques.getIntitule(), fruits.getIntitule());
        
        // 5.2 Deux types de graine
        TypeDeGraine basilic = TypeDeGraine.builder()
                .nom("basilic")
                .conseils("Le basilic est une plante aromatique qui aime la chaleur et le soleil. Arrosez régulièrement.")
                .semaineDebut(15)
                .semaineFin(25)
                .famille(aromatiques)
                .build();
        typeDeGraineRepository.save(basilic);
        
        TypeDeGraine tomateRoma = TypeDeGraine.builder()
                .nom("tomate Roma")
                .conseils("La tomate Roma est idéale pour les sauces. Plantez en plein soleil et tuteurez les plants.")
                .semaineDebut(10)
                .semaineFin(20)
                .famille(fruits)
                .build();
        typeDeGraineRepository.save(tomateRoma);
        
        log.info("Types de graine créés: {} et {}", basilic.getNom(), tomateRoma.getNom());
        
        // Créer un fournisseur
        Fournisseur fournisseur = Fournisseur.builder()
                .nom("Graines Bio")
                .telephone("0612345678")
                .build();
        fournisseurRepository.save(fournisseur);
        
        // 5.3 Un sachet contenant du basilic
        Sachet sachetBasilic = Sachet.builder()
                .prix(2.50)
                .nombreDeGraines(100)
                .typeDeGraine(basilic)
                .fournisseur(fournisseur)
                .build();
        sachetRepository.save(sachetBasilic);
        
        log.info("Sachet de basilic créé: {} graines à {} €", sachetBasilic.getNombreDeGraines(), sachetBasilic.getPrix());
        
        // 5.4 Un jardinier né le 01/01/1950
        Jardinier jardinier = Jardinier.builder()
                .nom("Dupont")
                .prenom("Jean")
                .dateDeNaissance(LocalDate.of(1950, 1, 1))
                .build();
        jardinierRepository.save(jardinier);
        
        log.info("Jardinier créé: {} {} né le {}", jardinier.getPrenom(), jardinier.getNom(), jardinier.getDateDeNaissance());
        
        // 5.5 Une commande effectuée aujourd'hui
        Commande commande = Commande.builder()
                .dateDeCommande(LocalDate.now())
                .jardinier(jardinier)
                .lignesDeCommande(new ArrayList<>())
                .build();
        
        LigneDeCommande ligne = LigneDeCommande.builder()
                .quantite(2)
                .sachet(sachetBasilic)
                .commande(commande)
                .build();
        
        commande.getLignesDeCommande().add(ligne);
        commandeRepository.save(commande);
        
        log.info("Commande créée le {} avec {} sachets de basilic", commande.getDateDeCommande(), ligne.getQuantite());
        log.info("=== Données initiales ajoutées avec succès ===\n");
    }
    
    public void afficherStatistiques() {
        log.info("=== STATISTIQUES ===\n");
        
        // 4.1 Commandes entre deux dates
        LocalDate dateDebut = LocalDate.now().minusDays(7);
        LocalDate dateFin = LocalDate.now();
        List<Commande> commandesEntreDates = commandeRepository.findCommandesEntreDeuxDates(dateDebut, dateFin);
        log.info("4.1 - Commandes entre {} et {} : {}", dateDebut, dateFin, commandesEntreDates.size());
        commandesEntreDates.forEach(c -> log.info("  - Commande #{} du {}", c.getId(), c.getDateDeCommande()));
        
        // 4.2 Sachets jamais commandés
        List<Sachet> sachetsJamaisCommandes = sachetRepository.findSachetsJamaisCommandes();
        log.info("\n4.2 - Sachets jamais commandés : {}", sachetsJamaisCommandes.size());
        sachetsJamaisCommandes.forEach(s -> log.info("  - Sachet #{} de {}", s.getId(), s.getTypeDeGraine().getNom()));
        
        // 4.3 Jardiniers ayant commandé du basilic
        List<Jardinier> jardiniersBasilic = jardinierRepository.findJardiniersAyantCommandeGraine("basilic");
        log.info("\n4.3 - Jardiniers ayant commandé du basilic : {}", jardiniersBasilic.size());
        jardiniersBasilic.forEach(j -> log.info("  - {} {}", j.getPrenom(), j.getNom()));
        
        // 4.4 Sachets triés par quantité commandée
        List<Sachet> sachetsTriesParQuantite = sachetRepository.findSachetsTriesParQuantiteCommandee();
        log.info("\n4.4 - Sachets triés par quantité commandée : {}", sachetsTriesParQuantite.size());
        sachetsTriesParQuantite.forEach(s -> {
            int totalCommande = s.getLignesDeCommande().stream()
                    .mapToInt(LigneDeCommande::getQuantite)
                    .sum();
            log.info("  - Sachet #{} de {} : {} commandés", s.getId(), s.getTypeDeGraine().getNom(), totalCommande);
        });
        
        // 4.5 Types de graine plantables aujourd'hui
        List<TypeDeGraine> typesPlantables = typeDeGraineRepository.findTypesPlantablesAujourdhui();
        log.info("\n4.5 - Types de graine plantables aujourd'hui : {}", typesPlantables.size());
        typesPlantables.forEach(t -> log.info("  - {} (semaines {} à {})", t.getNom(), t.getSemaineDebut(), t.getSemaineFin()));
        
        // 4.6 Recettes triées par nombre de types de graine
        List<Recette> recettesTriees = recetteRepository.findRecettesTrieesParNombreTypesDeGraine();
        log.info("\n4.6 - Recettes triées par nombre de types de graine : {}", recettesTriees.size());
        recettesTriees.forEach(r -> log.info("  - {} : {} types de graine", r.getIntitule(), r.getTypesDeGraine().size()));
        
        // 4.7 Jardiniers triés par nombre de commandes
        List<Jardinier> jardiniersTriesParCommandes = jardinierRepository.findJardiniersTriesParNombreCommandes();
        log.info("\n4.7 - Jardiniers triés par nombre de commandes : {}", jardiniersTriesParCommandes.size());
        jardiniersTriesParCommandes.forEach(j -> log.info("  - {} {} : {} commande(s)", 
                j.getPrenom(), j.getNom(), j.getCommandes().size()));
        
        // 4.8 Jardiniers de plus de 60 ans
        List<Jardinier> jardiniersPlus60 = jardinierRepository.findJardiniersDesPlusDe60Ans();
        log.info("\n4.8 - Jardiniers de plus de 60 ans : {}", jardiniersPlus60.size());
        jardiniersPlus60.forEach(j -> {
            int age = LocalDate.now().getYear() - j.getDateDeNaissance().getYear();
            log.info("  - {} {} ({} ans)", j.getPrenom(), j.getNom(), age);
        });
        
        // 4.9 Commandes triées par montant total
        List<Commande> commandesTrieesParMontant = commandeRepository.findCommandesTrieesParMontantTotal();
        log.info("\n4.9 - Commandes triées par montant total : {}", commandesTrieesParMontant.size());
        commandesTrieesParMontant.forEach(c -> log.info("  - Commande #{} : {} €", c.getId(), c.getMontantTotal()));
        
        // 4.10 Nombre de commandes par mois
        List<Object[]> commandesParMois = commandeRepository.countCommandesParMois();
        log.info("\n4.10 - Nombre de commandes par mois : {}", commandesParMois.size());
        commandesParMois.forEach(result -> {
            Integer annee = (Integer) result[0];
            Integer mois = (Integer) result[1];
            Long nombre = (Long) result[2];
            log.info("  - {}/{} : {} commande(s)", mois, annee, nombre);
        });
        
        log.info("\n=== FIN DES STATISTIQUES ===");
    }
}
