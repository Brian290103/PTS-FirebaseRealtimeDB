package com.example.patienttrackingfirebaserealtimedatabase;

public class PatientModel {

    String address,date,email,fname,gender,isApproved,lname,nid,pass,phone,role;

    public PatientModel(String address, String date, String email, String fname, String gender, String isApproved, String lname, String nid, String pass, String phone, String role) {
        this.address = address;
        this.date = date;
        this.email = email;
        this.fname = fname;
        this.gender = gender;
        this.isApproved = isApproved;
        this.lname = lname;
        this.nid = nid;
        this.pass = pass;
        this.phone = phone;
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getGender() {
        return gender;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public String getLname() {
        return lname;
    }

    public String getNid() {
        return nid;
    }

    public String getPass() {
        return pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
}
