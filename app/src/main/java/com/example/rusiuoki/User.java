package com.example.rusiuoki;

public class User {
    public String name;
    public String email;
    public String surename;

    public User(){
    }

    public User (String name, String surename,  String email){
        this.name = name;
        this.surename = surename;
        this.email = email;
    }
}
