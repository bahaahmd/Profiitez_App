package com.example.project2;



public class ProductHome {
    private String id;
    private String name;
    private String imageUrl;
    private String price_ancien;
    private String price_nouveau;
    private String date;
    private String description;
    private String categorie;
    private String idv;
    private Vendeur v;


    public Vendeur getV() {
        return v;
    }

    public void setV(Vendeur v) {
        this.v = v;
    }

    public ProductHome(){

    }


    public ProductHome(String id,String name, String  imageUrl, String price_ancien, String price_nouveau, String date, String description,String idv,Vendeur v,String categorie) {
        this.id=id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price_ancien = price_ancien;
        this.price_nouveau = price_nouveau;
        this.date = date;
        this.description = description;
        this.idv=idv;
        this.v=v;
        this.categorie=categorie;

    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice_ancien() {
        return price_ancien;
    }

    public void setPrice_ancien(String price_ancien) {
        this.price_ancien = price_ancien;
    }

    public String getPrice_nouveau() {
        return price_nouveau;
    }

    public void setPrice_nouveau(String price_nouveau) {
        this.price_nouveau = price_nouveau;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }


}
