package com.example.project2;

public class localisation {
    double longitude;
    double latitude;

    public localisation() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public localisation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}