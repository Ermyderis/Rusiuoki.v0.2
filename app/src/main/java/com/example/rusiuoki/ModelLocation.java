package com.example.rusiuoki;

public class ModelLocation {
    public String locationName;
    public String locationLatitude;
    public String locationLongitude;
    public String locationType;

    public ModelLocation(){
    }

    public ModelLocation(String locationName, String locationLatitude, String locationLongitude, String locationType){
        this.locationName = locationName;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.locationType = locationType;
    }

}
