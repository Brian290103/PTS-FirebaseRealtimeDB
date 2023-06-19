package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Notifications extends AppCompatActivity {
    private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("notifications");
    private ArrayList<NotificationModel> notificationModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notifications.this));

        Query query=databaseReference.orderByChild("patient_id").equalTo(Integer.toString(SharedPrefManager.getInstance(Notifications.this).getUserId()));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                notificationModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //  if(dataSnapshot.hasChild("id")&&dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("desc")) {

                    final String getId = dataSnapshot.child("id").getValue(String.class);
                    final String getPatientId = dataSnapshot.child("patient_id").getValue(String.class);
                    final String getReason = dataSnapshot.child("reason").getValue(String.class);
                    final String getDate = dataSnapshot.child("date").getValue(String.class);
                    final String getIsRead = dataSnapshot.child("isRead").getValue(String.class);


                    //  NotificationModel data = new NotificationModel(Integer.parseInt(getId), getName, getDesc);
                    NotificationModel data = new NotificationModel(getId,getPatientId,getIsRead,getReason,getDate);
                    notificationModels.add(data);
                    // Toast.makeText(MakeAppointment.this, "Id: ", Toast.LENGTH_SHORT).show();
                   /* }else{
                        Toast.makeText(MakeAppointment.this, "Error", Toast.LENGTH_SHORT).show();

                    }*/
                }

                Collections.reverse(notificationModels);
                recyclerView.setAdapter(new NotificationAdapter(Notifications.this,notificationModels));


                // NotificationAdapter.notifyDataSetChanged();
                // Toast.makeText(MakeAppointment.this, "Loading...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}