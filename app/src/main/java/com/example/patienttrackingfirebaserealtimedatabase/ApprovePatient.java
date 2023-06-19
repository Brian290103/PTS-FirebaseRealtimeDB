package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApprovePatient extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private ArrayList<PatientModel> patientModels = new ArrayList<>();
    RecyclerView recyclerViewPatientApprove;
    PatientAdapter patientAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_patient);

        progressDialog=new ProgressDialog(ApprovePatient.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerViewPatientApprove = findViewById(R.id.recyclerViewPatientApprove);
        recyclerViewPatientApprove.setHasFixedSize(true);
        recyclerViewPatientApprove.setLayoutManager(new LinearLayoutManager(ApprovePatient.this));

        Query query = databaseReference.orderByChild("isApproved").equalTo(0);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();

                patientModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    // doctorList.add(doctor);
                    //Toast.makeText(BookDoctor.this, "Getting Doctors", Toast.LENGTH_SHORT).show();
                    String getId = dataSnapshot.child("nid").getValue(String.class);
                    String getPhone = dataSnapshot.child("phone").getValue(String.class);
                    String getFName = dataSnapshot.child("fname").getValue(String.class);
                    String getLName = dataSnapshot.child("lname").getValue(String.class);
                    String getGender = dataSnapshot.child("gender").getValue(String.class);
                    String getEmail = dataSnapshot.child("email").getValue(String.class);
                    String getAddress = dataSnapshot.child("address").getValue(String.class);
                    String getDate = dataSnapshot.child("date").getValue(String.class);

                    PatientModel data = new PatientModel(getAddress, getDate, getEmail, getFName, getGender, "0", getLName, getId, "", getPhone, "patient");
                    //PatientModel data = new PatientModel("getAddress", "getDate", "getEmail", "getFName", "getGender", "0", "getLName", "getId", "", "getPhone", "patient");
                    patientModels.add(data);
                }

                /*PatientModel data = new PatientModel("getAddress", "getDate", "getEmail", "getFName", "getGender", "0", "getLName", "getId", "", "getPhone", "patient");
                patientModels.add(data);

                 */
                patientAdapter = new PatientAdapter(ApprovePatient.this, patientModels);
                recyclerViewPatientApprove.setAdapter(patientAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}