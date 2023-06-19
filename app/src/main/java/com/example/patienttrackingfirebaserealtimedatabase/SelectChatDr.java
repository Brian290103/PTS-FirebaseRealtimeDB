package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectChatDr extends AppCompatActivity {

    ArrayList<DoctorModel> doctorModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chat_dr);


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectChatDr.this));

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

        Query query=reference.orderByChild("role").equalTo("doctor");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                doctorModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String getId=dataSnapshot.child("nid").getValue(String.class);
                    String getPhone=dataSnapshot.child("phone").getValue(String.class);
                    String getFaculty=dataSnapshot.child("faculty").getValue(String.class);
                    int getIsPresent=dataSnapshot.child("isPresent").getValue(Integer.class);
                    String getFName=dataSnapshot.child("fname").getValue(String.class);
                    String getLName=dataSnapshot.child("lname").getValue(String.class);
                    String getGender=dataSnapshot.child("gender").getValue(String.class);
                    String getEmail=dataSnapshot.child("email").getValue(String.class);
                    String getAddress=dataSnapshot.child("address").getValue(String.class);
                    String getAvailableTimes=dataSnapshot.child("availableTimes").getValue(String.class);
                    String getDate=dataSnapshot.child("date").getValue(String.class);

                    //DoctorModel data = new DoctorModel(Integer.parseInt(getId),Integer.parseInt(getPhone),getFaculty,Integer.parseInt(getFaculty),"getFName","getLName","getGender","getEmail","getAddress","getAvailableTimes","getDate");
                    DoctorModel data = new DoctorModel(Integer.parseInt(getId),getPhone,getFaculty,getIsPresent,getFName,getLName,getGender,getEmail,getAddress,getAvailableTimes,getDate);
                    // DoctorModel data = new DoctorModel(Integer.parseInt(getId),Integer.parseInt(getPhone),getFaculty,Integer.parseInt(getIsPresent),getFName,getLName,getGender,getEmail,getAddress,getAvailableTimes,getDate);
                    doctorModels.add(data);
                }

                recyclerView.setAdapter( new SelectChatDrAdapter(SelectChatDr.this, doctorModels));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}