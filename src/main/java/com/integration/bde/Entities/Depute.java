package com.integration.bde.Entities;



import jakarta.persistence.*;

@Entity
public class Depute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;
    private String nom;
    private String email; // Pour l'authentification
    private String motDePasse; // Hashé

    // Getters et Setters
}
