package com.example.project2;

public class Market {
    private String nom;
    private String local;
    private String numero;
    private String ferme;
    private String ouvert;
    private String id;
    private String image;

    public Market(String nom, String local, String numero, String ferme, String ouvert,String id,String image) {
        this.nom = nom;
        this.local = local;
        this.numero = numero;
        this.ferme = ferme;
        this.ouvert = ouvert;
        this.id = id;
        this.image = image;
    }
    public Market(String nom, String local, String numero, String ferme, String ouvert,String id) {
        this.nom = nom;
        this.local = local;
        this.numero = numero;
        this.ferme = ferme;
        this.ouvert = ouvert;
        this.id = id;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFerme() {
        return ferme;
    }

    public void setFerme(String ferme) {
        this.ferme = ferme;
    }

    public String getOuvert() {
        return ouvert;
    }

    public void setOuvert(String ouvert) {
        this.ouvert = ouvert;
    }

    public Market() {
    }

}



