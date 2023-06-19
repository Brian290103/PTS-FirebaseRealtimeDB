package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddDoctor extends AppCompatActivity {

    TextInputLayout gender_layout, faculty_layout;
    AutoCompleteTextView gender_dropdown, faculty_dropdown;
    EditText txt_id_no, txt_fname, txt_lname, txt_phone, txt_email, txt_address, txt_pass, txt_cpass;
    Button reg_button;
    String faculty;
    String gender;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");

    ProgressDialog progressDialog;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_id_no = findViewById(R.id.txt_id_no);
        txt_fname = findViewById(R.id.txt_fname);
        txt_lname = findViewById(R.id.txt_lname);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_email);
        txt_address = findViewById(R.id.txt_address);
        txt_pass = findViewById(R.id.txtPass);
        txt_cpass = findViewById(R.id.txt_cpass);

        gender_layout = findViewById(R.id.gender_layout);
        gender_dropdown = findViewById(R.id.gender_dropdown);
        faculty_dropdown = findViewById(R.id.faculty_dropdown);
        String[] gender_list = {"Male", "Female"};

        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(AddDoctor.this, R.layout.gender_list, gender_list);
        gender_dropdown.setAdapter(gender_adapter);

        gender_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
        });


        faculty_layout = findViewById(R.id.faculty_layout);
        faculty_dropdown = findViewById(R.id.faculty_dropdown);
        //String[] faculty_list = {"Dentist", "Optometry","Cardiology"};

        ArrayList<String> faculty_list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String facultyName = dataSnapshot.child("name").getValue(String.class);
                    faculty_list.add(facultyName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<>(AddDoctor.this, R.layout.faculty_list, faculty_list);
        faculty_dropdown.setAdapter(faculty_adapter);

        faculty_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                faculty = parent.getItemAtPosition(position).toString();
            }
        });


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(AddDoctor.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String id = txt_id_no.getText().toString();
                String fname = txt_fname.getText().toString();
                String lname = txt_lname.getText().toString();
                String phone = txt_phone.getText().toString();
                String email = txt_email.getText().toString();
                String address = txt_address.getText().toString();
                String pass = txt_pass.getText().toString();
                String cpass = txt_cpass.getText().toString();

                String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {
                    Toast.makeText(AddDoctor.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (!email.matches(checkspaces)) {
                    Toast.makeText(AddDoctor.this, "Invalid email entered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (!pass.equals(cpass)) {
                    Toast.makeText(AddDoctor.this, "The two passwords don't match", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

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

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            progressDialog.dismiss();

                            if (snapshot.hasChild(id)) {
                                Toast.makeText(AddDoctor.this, "That Id Number already Exist. Choose another", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("users").child(id).child("nid").setValue(id);
                                databaseReference.child("users").child(id).child("fname").setValue(fname);
                                databaseReference.child("users").child(id).child("lname").setValue(lname);
                                databaseReference.child("users").child(id).child("gender").setValue(gender);
                                databaseReference.child("users").child(id).child("email").setValue(email);
                                databaseReference.child("users").child(id).child("phone").setValue(phone);
                                databaseReference.child("users").child(id).child("address").setValue(address);
                                databaseReference.child("users").child(id).child("faculty").setValue(faculty);
                                databaseReference.child("users").child(id).child("pass").setValue(pass);
                                databaseReference.child("users").child(id).child("role").setValue("doctor");
                                databaseReference.child("users").child(id).child("isPresent").setValue(0);
                                databaseReference.child("users").child(id).child("isApproved").setValue(1);
                                databaseReference.child("users").child(id).child("availableTimes").setValue("");
                                databaseReference.child("users").child(id).child("date").setValue(date);

                                Toast.makeText(AddDoctor.this, "Doctor Added Successful", Toast.LENGTH_SHORT).show();

                                finish();

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

    }
}