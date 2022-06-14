package com.example.project2;

public class Vendeur {
String nom;
String ouvert;
String fermer;
String image;
String tel;



    public Vendeur() {
    }

    public Vendeur(String nom, String ouvert, String fermer,String image) {
        this.nom = nom;
        this.ouvert = ouvert;
        this.fermer = fermer;
        this.image = image;

    }
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
