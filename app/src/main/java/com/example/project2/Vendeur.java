package com.example.project2;

public class Vendeur {
String nom;
String ouvert;
String fermer;
String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Vendeur() {
    }

    public Vendeur(String nom, String ouvert, String fermer,String image) {
        this.nom = nom;
        this.ouvert = ouvert;
        this.fermer = fermer;
        this.image = image;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getOuvert() {
        return ouvert;
    }

    public void setOuvert(String ouvert) {
        this.ouvert = ouvert;
    }

    public String getFermer() {
        return fermer;
    }

    public void setFermer(String fermer) {
        this.fermer = fermer;
    }
}
