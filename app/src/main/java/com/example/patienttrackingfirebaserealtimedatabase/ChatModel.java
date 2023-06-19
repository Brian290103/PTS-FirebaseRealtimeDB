package com.example.patienttrackingfirebaserealtimedatabase;

public class ChatModel {
    String id,from,to,message,date,name;

    public ChatModel(String id, String from, String to, String message, String date,String name) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
}
