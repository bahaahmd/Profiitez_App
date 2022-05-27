package com.example.project2;

import android.net.Uri;
import android.widget.ImageView;

public class user {
    private String userName,date,commentaire,id;
    private String image;
    public  user (){

    }
    public user(String id,String userName, String date, String commentaire, String image) {
        this.id=id;
        this.userName = userName;
        this.date = date;
        this.commentaire = commentaire;
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

}
