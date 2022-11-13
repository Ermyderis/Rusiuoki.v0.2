package com.example.rusiuoki;

public class ModelUser {
    public String name;
    public String email;
    public String surename;

    public ModelUser(){
    }

    public ModelUser(String name, String surename, String email){
        this.name = name;
        this.surename = surename;
        this.email = email;
    }
}
