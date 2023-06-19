package com.example.patienttrackingfirebaserealtimedatabase;

public class DoctorModel {

    private int id, isPresent;
    private String fname, lname, gender, email, address, available_times, date,faculty,phone;

    public DoctorModel(int id, String phone, String faculty, int isPresent, String fname, String lname, String gender, String email, String address, String available_times, String date) {

        this.id = id;
        this.phone = phone;
        this.faculty = faculty;
        this.isPresent = isPresent;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.available_times = available_times;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getAvailable_times() {
        return available_times;
    }

    public String getDate() {
        return date;
    }
}
