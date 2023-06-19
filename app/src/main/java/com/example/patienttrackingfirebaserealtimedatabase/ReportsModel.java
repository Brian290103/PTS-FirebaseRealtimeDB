package com.example.patienttrackingfirebaserealtimedatabase;

public class ReportsModel {
    String date,doctor_id,id,patient_id,r_comments,r_possible_disease,r_symptoms,faculty;

    public ReportsModel(String date, String doctor_id, String id, String patient_id, String r_comments, String r_possible_disease, String r_symptoms, String faculty) {
        this.date = date;
        this.doctor_id = doctor_id;
        this.id = id;
        this.patient_id = patient_id;
        this.r_comments = r_comments;
        this.r_possible_disease = r_possible_disease;
        this.r_symptoms = r_symptoms;
        this.faculty = faculty;
    }

    public String getDate() {
        return date;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getId() {
        return id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public String getR_comments() {
        return r_comments;
    }

    public String getR_possible_disease() {
        return r_possible_disease;
    }

    public String getR_symptoms() {
        return r_symptoms;
    }
}
