package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddPatientMedicalReport extends AppCompatActivity {

    TextView tvIdNo, tvFName, tvLName, txtGender, txtPhone;
    EditText txtSymptoms, txtComments, txtPossibleDisease;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");

    Button btnSubmit;
    String date;
    private String idNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_medical_report);

        tvIdNo = findViewById(R.id.TvIdNo);
        tvFName = findViewById(R.id.tvFName);
        tvLName = findViewById(R.id.tvLName);
        txtGender = findViewById(R.id.txtGender);
        txtPhone = findViewById(R.id.txtPhone);
        txtSymptoms = findViewById(R.id.txtSymptoms);
        txtPossibleDisease = findViewById(R.id.txtPossibleDisease);
        txtComments = findViewById(R.id.txtComments);
        btnSubmit=findViewById(R.id.btnSubmit);

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                String symptoms=txtSymptoms.getText().toString();
                String possibleDisease=txtPossibleDisease.getText().toString();
                String comments=txtComments.getText().toString();

                if (symptoms.isEmpty() || possibleDisease.isEmpty() || comments.isEmpty()) {
                    Toast.makeText(AddPatientMedicalReport.this, "Kindly fill in all the records", Toast.LENGTH_SHORT).show();
                    //btnSubmit.setEnabled(false);
                } else {
                    progressDialog.setMessage("Submitting...");
                    progressDialog.show();

                    String id = databaseReference.push().getKey();

                    DateTimeFormatter dtf = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    }
                    LocalDateTime now = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        now = LocalDateTime.now();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        date = dtf.format(now);
                    }

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            databaseReference.child("reports").child(id).child("id").setValue(id);
                            databaseReference.child("reports").child(id).child("doctor_id").setValue(Integer.toString(SharedPrefManager.getInstance(AddPatientMedicalReport.this).getUserId()));
                            databaseReference.child("reports").child(id).child("patient_id").setValue(idNo);
                            databaseReference.child("reports").child(id).child("report").child("symptoms").setValue(txtSymptoms.getText().toString());
                            databaseReference.child("reports").child(id).child("report").child("possible_disease").setValue(txtPossibleDisease.getText().toString());
                            databaseReference.child("reports").child(id).child("report").child("comments").setValue(txtComments.getText().toString());
                            databaseReference.child("reports").child(id).child("date").setValue(date);

                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();

                        }
                    });

                }


            }
        });

        progressDialog = new ProgressDialog(AddPatientMedicalReport.this);

        findViewById(R.id.findBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tvIdNo.getText().toString();

                if (id.isEmpty()) {
                    Toast.makeText(AddPatientMedicalReport.this, "The Id Field is black", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Getting Records");
                    progressDialog.show();

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            progressDialog.dismiss();

                            if (snapshot.hasChild(id)) {

                                btnSubmit.setEnabled(true);

                                idNo = snapshot.child(id).child("nid").getValue().toString();
                                final String getPass = snapshot.child(id).child("pass").getValue().toString();
                                final String getRole = snapshot.child(id).child("role").getValue().toString();
                                final String getIsApproved = snapshot.child(id).child("isApproved").getValue().toString();
                                final String get_fname = snapshot.child(id).child("fname").getValue().toString();
                                final String get_lname = snapshot.child(id).child("lname").getValue().toString();
                                final String getEmail = snapshot.child(id).child("email").getValue().toString();
                                final String getPhone = snapshot.child(id).child("phone").getValue().toString();
                                final String getGender = snapshot.child(id).child("gender").getValue().toString();


                                tvFName.setText(get_fname);
                                tvLName.setText(get_lname);
                                txtGender.setText(getGender);
                                txtPhone.setText(getPhone);

                            } else {
                                Toast.makeText(AddPatientMedicalReport.this, "Id Not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });

                }

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