package com.example.project2;


public class Search_item {
    String name,id;
    String ImageUrl;

    public  Search_item(){

    }

    public Search_item(String name, String imageUrl,String id) {
        this.name = name;
        this.ImageUrl = imageUrl;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
