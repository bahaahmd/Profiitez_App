package com.example.project2;

public class Notif {
    public String ImageUrl;
    public String title;
    public String description;
    public String timer;
    public Notif(){

    }
    public Notif(String imageUrl, String title, String description, String timer) {
        ImageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.timer = timer;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}
