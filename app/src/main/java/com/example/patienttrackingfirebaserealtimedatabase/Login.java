package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextInputEditText txt_n_id, txt_pass;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

            if (SharedPrefManager.getInstance(this).getRole().equals("patient")) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));

            } else if (SharedPrefManager.getInstance(this).getRole().equals("doctor")) {
                startActivity(new Intent(getApplicationContext(), DashboardDr.class));

            } else if (SharedPrefManager.getInstance(this).getRole().equals("admin")) {
                startActivity(new Intent(getApplicationContext(), DashboardAdmin.class));

            }
            return;
        }


        txt_n_id = findViewById(R.id.txt_n_id);
        txt_pass = findViewById(R.id.txt_pass);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String id = txt_n_id.getText().toString();
                        String pass = txt_pass.getText().toString();

                        if (id.isEmpty() || pass.isEmpty()) {
                            Toast.makeText(Login.this, "Kindly fill in all the fields", Toast.LENGTH_SHORT).show();
                        } else {


                            if (snapshot.hasChild(id)) {

                                final String getId = snapshot.child(id).child("nid").getValue().toString();
                                final String getPass = snapshot.child(id).child("pass").getValue().toString();
                                final String getRole = snapshot.child(id).child("role").getValue().toString();
                                final String getIsApproved = snapshot.child(id).child("isApproved").getValue().toString();
                                final String get_fname = snapshot.child(id).child("fname").getValue().toString();
                                final String get_lname = snapshot.child(id).child("lname").getValue().toString();
                                final String getEmail = snapshot.child(id).child("email").getValue().toString();

                                if (getPass.equals(pass)) {
                                    //Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(getApplicationContext(),DashboardDr.class));


                                    if (getRole.equals("patient")) {
                                        if (getIsApproved.equals("0")) {
                                            Toast.makeText(Login.this, "You have not been Approved Yet, Contact the Admin", Toast.LENGTH_SHORT).show();
                                        } else {

                                            SharedPrefManager.getInstance(Login.this)
                                                    .userLogin(Integer.parseInt(id), get_fname, get_lname, "NA", "NA", getEmail, "NA", "NA", "NA", 0, "NA", "patient");

                                            Toast.makeText(Login.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login.this, Dashboard.class));
                                        }

                                    } else if (getRole.equals("doctor")) {
                                        SharedPrefManager.getInstance(Login.this)
                                                .userLogin(Integer.parseInt(id), get_fname, get_lname, "NA", "NA", getEmail, "NA", "NA", "NA", 0, "NA", "doctor");

                                        Toast.makeText(Login.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, DashboardDr.class));

                                    } else if (getRole.equals("admin")) {

                                        SharedPrefManager.getInstance(Login.this)
                                                .userLogin(Integer.parseInt(id), get_fname, get_lname, "NA", "NA", getEmail, "NA", "NA", "NA", 0, "NA", "admin");

                                        Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, DashboardAdmin.class));
                                    }


                                } else {
                                    Toast.makeText(Login.this, "Login Failed, Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(Login.this, "Login Failed, Register First before logging in", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        findViewById(R.id.btn_sign_up).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login.this, Register.class));
                    }
                });

    }
}