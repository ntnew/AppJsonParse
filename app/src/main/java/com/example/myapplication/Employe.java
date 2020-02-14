package com.example.myapplication;

public class Employe {
    public String name;
    public String phone_number;
    public String skills;

    public Employe(String name, String phone_number,String skills){
        this.name = name;
        this.phone_number = phone_number;
        this.skills = skills;

    }
    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone_number;
    }
    public String getSkills() {return this.skills;}
}
