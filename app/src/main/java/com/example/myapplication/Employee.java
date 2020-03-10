package com.example.myapplication;

public class Employee {
    public String name;
    private String phone_number;
    private String skills;

    Employee(String name, String phone_number, String skills){
        this.name = name;
        this.phone_number = phone_number;
        this.skills = skills;
    }
    public String getName() {
        return this.name;
    }
    String getPhone() {
        return this.phone_number;
    }
    String getSkills() {return this.skills;}
}
