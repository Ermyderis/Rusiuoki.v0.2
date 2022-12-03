package com.example.rusiuoki;

public class ModelBarCode {
    public String barCode;
    public String packageName;
    public String packageType;
    public String packageRecyclePlace;
    public String activityType;

    public ModelBarCode(){
    }

    public ModelBarCode(String barCode, String packageName, String packageType, String packageRecyclePlace, String activityType){
        this.barCode = barCode;
        this.packageName = packageName;
        this.packageType = packageType;
        this.packageRecyclePlace = packageRecyclePlace;
        this.activityType = activityType;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public String getPackageRecyclePlace() {
        return packageRecyclePlace;
    }

    public String getActivityType() {
        return activityType;
    }
}

