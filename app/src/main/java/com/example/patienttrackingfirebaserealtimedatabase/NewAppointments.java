package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class NewAppointments extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointments");
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    private ArrayList<AppointmentsModel> appointmentsModels = new ArrayList<>();

    private String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointments);


        findViewById(R.id.btn_back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final RecyclerView recycler_view = findViewById(R.id.recycler_view);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(NewAppointments.this));

        Query query = databaseReference.orderByChild("doctor_id").equalTo(Integer.toString(SharedPrefManager.getInstance(NewAppointments.this).getUserId()));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                appointmentsModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //  if(dataSnapshot.hasChild("id")&&dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("desc")) {

                    final String getAppDate = dataSnapshot.child("app_date").getValue(String.class);
                    final String getAppTime = dataSnapshot.child("app_time").getValue(String.class);
                    final String getDate = dataSnapshot.child("date").getValue(String.class);
                    final String getDoctorId = dataSnapshot.child("doctor_id").getValue(String.class);
                    final String getFaculty = dataSnapshot.child("faculty").getValue(String.class);
                    final String getId = dataSnapshot.child("id").getValue(String.class);
                    final String getIsApproved = dataSnapshot.child("isApproved").getValue(String.class);
                    final String getIsPaid = dataSnapshot.child("isPaid").getValue(String.class);
                    final String getMessage = dataSnapshot.child("message").getValue(String.class);
                    patient_id = dataSnapshot.child("patient_id").getValue(String.class);


                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //  appointmentsModels.clear();

                            if (snapshot.hasChild(patient_id)) {
                                //Toast.makeText(NewAppointments.this, "patient Id gotten", Toast.LENGTH_SHORT).show();
                                String getFName = snapshot.child(patient_id).child("fname").getValue().toString();
                                String getLName = snapshot.child(patient_id).child("lname").getValue().toString();

                                String getName = getFName + " " + getLName;

                                appointmentsModels.add(new AppointmentsModel(getAppDate, getAppTime, getDate, getDoctorId, getFaculty, getId, getIsApproved, getIsPaid, getMessage,patient_id, getName));

                                Collections.reverse(appointmentsModels);
                                recycler_view.setAdapter(new AppointmentsAdapter(NewAppointments.this, appointmentsModels));


                            } else {
                                Toast.makeText(NewAppointments.this, "patient Id not gotten", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                        }
                    });



                 /*   getPatientName(patient_id, new Callback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //AppointmentsModel data = new AppointmentsModel(getAppDate, getAppTime, getDate, getDoctorId, getFaculty, getId, getIsApproved, getIsPaid, getMessage, patientName);
                            //Log.w("dog1","Name: "+result);
                            //appointmentsModels.clear();
                            //AppointmentsModel data = new AppointmentsModel("getAppDate", "getAppTime", "getDate", "getDoctorId", "getFaculty", "getId", "getIsApproved", "getIsPaid", "getMessage", "patientName");
                            appointmentsModels.add(new AppointmentsModel(getAppDate, getAppTime, getDate, getDoctorId, getFaculty, getId, getIsApproved, getIsPaid, getMessage, result));

                            recycler_view.setAdapter(new AppointmentsAdapter(NewAppointments.this, appointmentsModels));
                           // recycler_view.getAdapter().notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Exception exception) {

                        }
                    });

                  */

                }


                // NotificationAdapter.notifyDataSetChanged();
                // Toast.makeText(MakeAppointment.this, "Loading...", Toast.LENGTH_SHORT).show();

               /* Intent intent = getIntent();
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);

                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPatientName(String patient_id, final Callback<String> callback) {

        FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/")
                .child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(patient_id)) {

                            final String getFName = snapshot.child(patient_id).child("fname").getValue().toString();
                            final String getLName = snapshot.child(patient_id).child("lname").getValue().toString();

                            String name = getFName + " " + getLName;
                            callback.onSuccess(name);


                        } else {
                            Toast.makeText(NewAppointments.this, "No ID", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    interface Callback<T> {
        void onSuccess(T result);

        void onFailure(Exception exception);
    }
}