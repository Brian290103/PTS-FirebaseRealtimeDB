package com.example.patienttrackingfirebaserealtimedatabase;

public class SpecialtyModel {
    private  String id;
    private   String name,desc;

    public SpecialtyModel(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

}