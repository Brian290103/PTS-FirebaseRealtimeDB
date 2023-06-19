package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardDr extends AppCompatActivity {

    ConstraintLayout btn_new_appointments;
    ConstraintLayout btn_add_patient;
    ConstraintLayout btn_addPatientMedicalReport;
    TextView tvDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_dr);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        findViewById(R.id.btnViewReports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDr.this, ViewReports.class));
            }
        });
        findViewById(R.id.btnAddPatientMedicalProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDr.this, AddPatientMedicalReport.class));
            }
        });

        findViewById(R.id.btnManageProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardDr.this, ManageProfile.class);

                intent.putExtra("view", "doctor");

                startActivity(intent);
            }
        });

        findViewById(R.id.chat_dr_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorChatDr.class));
            }
        });

        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvDoctorName.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsername());


        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(DashboardDr.this).logOut();
                Toast.makeText(DashboardDr.this, "Admin Logged Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        findViewById(R.id.logout_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(DashboardDr.this).logOut();
                Toast.makeText(DashboardDr.this, "Admin Logged Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


        findViewById(R.id.btn_newAppointments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDr.this, NewAppointments.class));
            }
        });

        findViewById(R.id.btn_approvePatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDr.this, AddPatient.class));
            }
        });
    }
}