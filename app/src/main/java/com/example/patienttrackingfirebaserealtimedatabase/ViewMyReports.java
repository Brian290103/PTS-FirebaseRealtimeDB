package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewMyReports extends AppCompatActivity {

    ArrayList<ReportsModel> reportsModels = new ArrayList<>();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("reports");
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_reports);

        progressDialog = new ProgressDialog(ViewMyReports.this);
        progressDialog.setMessage("Getting records...");
        progressDialog.show();
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewMyReports.this));

        Query query = databaseReference.orderByChild("patient_id").equalTo(Integer.toString(SharedPrefManager.getInstance(ViewMyReports.this).getUserId()));
        // Query query=databaseReference.orderByChild("patient_id").equalTo();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                reportsModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String getId = dataSnapshot.child("id").getValue(String.class);
                    String getDate = dataSnapshot.child("date").getValue(String.class);
                    String getDoctorId = dataSnapshot.child("doctor_id").getValue(String.class);
                    String getPatientId = dataSnapshot.child("patient_id").getValue(String.class);
                    String getRComments = dataSnapshot.child("report").child("comments").getValue(String.class);
                    String getRPossibleDisease = dataSnapshot.child("report").child("possible_disease").getValue(String.class);
                    String getSymptoms = dataSnapshot.child("report").child("symptoms").getValue(String.class);

                    //final String[] patientName = {""};


                    FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                              //reportsModels.clear();

                            if (snapshot.hasChild(getDoctorId)) {
                                // Toast.makeText(ViewMyReports.this, "patient Id gotten: "+ snapshot.child(getDoctorId).child("fname").getValue().toString(), Toast.LENGTH_SHORT).show();
                                String getFName = snapshot.child(getDoctorId).child("fname").getValue().toString();
                                String getLName = snapshot.child(getDoctorId).child("lname").getValue().toString();
                                String getFaculty = snapshot.child(getDoctorId).child("faculty").getValue().toString();

                                String getName = getFName + " " + getLName;
/*
                                appointmentsModels.add(new AppointmentsModel(getAppDate, getAppTime, getDate, getDoctorId, getFaculty, getId, getIsApproved, getIsPaid, getMessage,patient_id, getName));

                                Collections.reverse(appointmentsModels);
                                recycler_view.setAdapter(new AppointmentsAdapter(NewAppointments.this, appointmentsModels));


*/

                                ReportsModel data = new ReportsModel(getDate, getName, getId, getPatientId, getRComments, getRPossibleDisease, getSymptoms,getFaculty);
                                reportsModels.add(data);
                                recyclerView.setAdapter(new ReportsAdapter(reportsModels, ViewMyReports.this));


                            } else {
                                //Toast.makeText(NewAppointments.this, "patient Id not gotten", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                        }
                    });



                /*    Query query = databaseReference.child("users").orderByChild("nid").equalTo(getPatientId);
                    query.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           String patientName = snapshot.child("fname").getValue(String.class);
                            Toast.makeText(ViewReports.this, "Fname: "+patientName, Toast.LENGTH_SHORT).show();
                            ReportsModel data = new ReportsModel(getDate, getDoctorId, patientName, getPatientId, getRComments, getRPossibleDisease, getSymptoms);
                            reportsModels.add(data);
                            recyclerView.setAdapter(new ReportsAdapter(reportsModels, ViewReports.this));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    */

                    //ReportsModel data = new ReportsModel("date", "1", "2", "3", "comments", "disease", "symptoms");
                    // ReportsModel data = new ReportsModel(getDate, getDoctorId, getId, getPatientId, getRComments, getRPossibleDisease, getSymptoms);
                    // reportsModels.add(data);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

    }
}