package com.example.rusiuoki;

public class ModelLocation {
    public String locationAdres;
    public String locationLatitude;
    public String locationLongitude;
    public String locationType;

    public ModelLocation(){
    }

    public ModelLocation(String locationAdres, String locationLatitude, String locationLongitude, String locationType){
        this.locationAdres = locationAdres;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.locationType = locationType;
    }

}
