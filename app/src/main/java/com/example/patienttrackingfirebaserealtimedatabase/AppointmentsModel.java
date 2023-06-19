package com.example.patienttrackingfirebaserealtimedatabase;

public class AppointmentsModel {

   String appDate,appTime,date,doctorId,faculty,id,isApproved,isPaid,message,patientId,patientName;

    public AppointmentsModel(String appDate, String appTime, String date, String doctorId, String faculty, String id, String isApproved, String isPaid, String message, String patientId,String patientName) {
        this.appDate = appDate;
        this.appTime = appTime;
        this.date = date;
        this.doctorId = doctorId;
        this.faculty = faculty;
        this.id = id;
        this.isApproved = isApproved;
        this.isPaid = isPaid;
        this.message = message;
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public String getAppDate() {
        return appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public String getDate() {
        return date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getId() {
        return id;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public String getMessage() {
        return message;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }
}
