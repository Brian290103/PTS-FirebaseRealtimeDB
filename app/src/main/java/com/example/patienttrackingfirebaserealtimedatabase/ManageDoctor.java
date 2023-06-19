package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageDoctor extends AppCompatActivity {
    ConstraintLayout btn_addSpeciality;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");

    ArrayList<String> faculty_list = new ArrayList<>();

    Spinner categoriesSpinner;
    Spinner facultySpinner;

    ProgressDialog progressDialog;
    String category;
    private RecyclerView recyclerView;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    ListDoctorsAdapters listDoctorsAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctor);

        listDoctorsAdapters = new ListDoctorsAdapters(ManageDoctor.this, doctorModels);
        recyclerView = findViewById(R.id.recyclerViewDoctors);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
        //DoctorModel data=new DoctorModel(1,1,"faculty",1,"fname","lname","gender","email","address","times","date");
        // doctorModels.add(data);

        progressDialog = new ProgressDialog(ManageDoctor.this);
        progressDialog.setMessage("Getting records...");
        progressDialog.show();

        getAllRecords();




        // recyclerView.setAdapter(new ListDoctorsAdapters(ManageDoctor.this,doctorModels));


        findViewById(R.id.btn_addPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageDoctor.this, AddDoctor.class));
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        categoriesSpinner = findViewById(R.id.spinnerCategories);
        facultySpinner = findViewById(R.id.facultySpinner);

        String[] categories_list = {"Select Category", "Faculty", "Doctor id"};

        ArrayAdapter categoriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories_list);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        categoriesSpinner.setAdapter(categoriesAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(),categories_list[position] , Toast.LENGTH_LONG).show();


                if (position == 0) {
                    getAllRecords();
                    faculty_list.clear();

                    findViewById(R.id.faculty_layout).setVisibility(View.GONE);
                    findViewById(R.id.doctor_id_layout).setVisibility(View.GONE);

                    databaseReference.child("faculty").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            faculty_list.clear();
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


                } else

                 if (position == 1) {

                    category = "faculty";
                    findViewById(R.id.faculty_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.doctor_id_layout).setVisibility(View.GONE);

                    databaseReference.child("faculty").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            faculty_list.clear();
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

                    ArrayAdapter<String> faculty_adapter = new ArrayAdapter<>(ManageDoctor.this, android.R.layout.simple_spinner_item, faculty_list);
                    faculty_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    facultySpinner.setAdapter(faculty_adapter);


                    facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Toast.makeText(getApplicationContext(),faculty_list.get(position) , Toast.LENGTH_LONG).show();
                            searchArrayList(faculty_list.get(position), "faculty");

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // findViewById(R.id.faculty_layout).setVisibility(View.VISIBLE);

                        }
                    });


                } else if (position == 2) {

                    findViewById(R.id.faculty_layout).setVisibility(View.GONE);
                    findViewById(R.id.doctor_id_layout).setVisibility(View.VISIBLE);

                    EditText drID=findViewById(R.id.TvDrId);
                    findViewById(R.id.btnFind).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(drID.getText().toString().isEmpty()){
                                Toast.makeText(ManageDoctor.this, "Nothing has been entered", Toast.LENGTH_SHORT).show();
                            }else {
                                searchArrayList(drID.getText().toString(), "doctorId");
                            }
                        }
                    });

                } else {
                    Toast.makeText(ManageDoctor.this, "No Category Selected", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.faculty_layout).setVisibility(View.GONE);
                    findViewById(R.id.doctor_id_layout).setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_addSpeciality = findViewById(R.id.btn_addSpeciality);

        btn_addSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(ManageDoctor.this);
                progressDialog.setMessage("Adding Faculty");

                AlertDialog.Builder builder = new AlertDialog.Builder(ManageDoctor.this);

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_speciality, null);
                EditText txtFacultyName = view.findViewById(R.id.txt_faculty_name);
                EditText txtFacultyDesc = view.findViewById(R.id.txt_faculty_desc);

                builder.setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog.show();

                                String faculty = txtFacultyName.getText().toString();
                                String facultyDesc = txtFacultyDesc.getText().toString();

                                if (faculty.isEmpty() || facultyDesc.isEmpty()) {
                                    Toast.makeText(ManageDoctor.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    databaseReference.child("faculty").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String facultyId = databaseReference.push().getKey();

                                            databaseReference.child("faculty").child(facultyId).child("id").setValue(facultyId);
                                            databaseReference.child("faculty").child(facultyId).child("name").setValue(faculty);

                                            databaseReference.child("faculty").child(facultyId).child("desc").setValue(facultyDesc);

                                            Toast.makeText(ManageDoctor.this, "Faculty Added Successful", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }


                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            progressDialog.dismiss();

                                        }
                                    });
                                }


                            }
                        })
                        .setView(view);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                //startActivity(new Intent(getApplicationContext(),AddDoctor.class));

            }
        });
    }

    private void getAllRecords() {

        Query query = databaseReference.child("users").orderByChild("role").equalTo("doctor");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                doctorModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String getId = dataSnapshot.child("nid").getValue(String.class);
                    String getPhone = dataSnapshot.child("phone").getValue(String.class);
                    String getFaculty = dataSnapshot.child("faculty").getValue(String.class);
                    int getIsPresent = dataSnapshot.child("isPresent").getValue(Integer.class);
                    String getFName = dataSnapshot.child("fname").getValue(String.class);
                    String getLName = dataSnapshot.child("lname").getValue(String.class);
                    String getGender = dataSnapshot.child("gender").getValue(String.class);
                    String getEmail = dataSnapshot.child("email").getValue(String.class);
                    String getAddress = dataSnapshot.child("address").getValue(String.class);
                    String getAvailableTimes = dataSnapshot.child("availableTimes").getValue(String.class);
                    String getDate = dataSnapshot.child("date").getValue(String.class);

                    //  Toast.makeText(ManageDoctor.this, "Id: "+getId, Toast.LENGTH_SHORT).show();
                    //Integer.parseInt(getPhone)
                    //Integer.parseInt(getId)
                    DoctorModel data = new DoctorModel(Integer.parseInt(getId), getPhone, getFaculty, getIsPresent, getFName, getLName, getGender, getEmail, getAddress, getAvailableTimes, getDate);
                    // DoctorModel data = new DoctorModel(Integer.parseInt(getId),Integer.parseInt(getPhone),getFaculty,Integer.parseInt(getIsPresent),getFName,getLName,getGender,getEmail,getAddress,getAvailableTimes,getDate);
                    doctorModels.add(data);

                    // Toast.makeText(ManageDoctor.this, "Id: "+getId, Toast.LENGTH_SHORT).show();
                }

                recyclerView.setAdapter(listDoctorsAdapters);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Log.w("Firebase Error", "Error: " + error.getMessage());
            }
        });


    }

    private void searchArrayList(String newText, String cat) {
        ArrayList<DoctorModel> dataSearchArrayList = new ArrayList<>();

        if (cat.equals("faculty")) {
            for (DoctorModel data : doctorModels) {
                if (data.getFaculty().toLowerCase().contains(newText.toLowerCase())) {
                    dataSearchArrayList.add(data);
                }
            }
        }else  if(cat.equals("doctorId")){
            for (DoctorModel data : doctorModels) {
                if (data.getId()==Integer.parseInt(newText)) {
                    dataSearchArrayList.add(data);
                }
            }
        }
        else {
            for (DoctorModel data : doctorModels) {

                String id = Integer.toString(data.getId());

                if (id.contains(newText)) {
                    dataSearchArrayList.add(data);
                }
            }
        }


        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            listDoctorsAdapters.setSearchList(dataSearchArrayList);
        }
    }
}