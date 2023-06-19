package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ManagePatient extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("reports");

    ArrayList<String> faculty_list = new ArrayList<>();

    Spinner categoriesSpinner;
    Spinner facultySpinner;

    ProgressDialog progressDialog;
    String category;
    RecyclerView recyclerView;
    ArrayList<PatientModel> patientModels = new ArrayList<>();
    ListPatientAdapters listPatientAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patient);

        recyclerView = findViewById(R.id.recyclerViewPatients);

        progressDialog = new ProgressDialog(ManagePatient.this);
        progressDialog.setMessage("Getting records...");
        progressDialog.show();

        //PatientModel data = new PatientModel("getAddress","getDate","getEmail","getFName","getGender","1","getLName","getId","","getPhone","");
        // patientModels.add(data);
        listPatientAdapters = new ListPatientAdapters(ManagePatient.this, patientModels);
        //recyclerView.setAdapter(new PatientAdapter(ManagePatient.this,models));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManagePatient.this));
        //recyclerView.setAdapter(new ListPatientAdapters(ManagePatient.this, patientModels));

        getAllRecords();

        findViewById(R.id.btn_approvePatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagePatient.this, ApprovePatient.class));
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.btn_addPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagePatient.this, AddPatient.class));
            }
        });
    }


    private void getAllRecords() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();

                for (DataSnapshot reportSnapshot : snapshot.getChildren()) {
                    String getPatientId = reportSnapshot.child("patient_id").getValue(String.class);
                    String getDisease = reportSnapshot.child("possible_disease").getValue(String.class);


                    FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(getPatientId)) {
                                Toast.makeText(ManagePatient.this, "Patient Exists", Toast.LENGTH_SHORT).show();

                                String getFName = snapshot.child(getPatientId).child("fname").getValue(String.class);
                                String getLName = snapshot.child(getPatientId).child("lname").getValue(String.class);

                                //String name=getFName+" "+getLName;

                                PatientModel data = new PatientModel(getDisease, "getDate", "getEmail", getFName, "getGender", "1", getLName, getPatientId, "", "getPhone", "");
                                patientModels.add(data);

                            }

                            recyclerView.setAdapter(new ListPatientAdapters(ManagePatient.this, patientModels));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    //Toast.makeText(ManagePatient.this, "Patient Id: "+getPatientId, Toast.LENGTH_SHORT).show();

                }

                //String getPatientId=snapshot.child("patient_id").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

        findViewById(R.id.btnFindDisease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtDisease=findViewById(R.id.txtDisease);
                String newText=txtDisease.getText().toString();
                String category="disease";

                if(newText.isEmpty()){
                    Toast.makeText(ManagePatient.this, "Invalid value entered", Toast.LENGTH_SHORT).show();
                }else {
                    searchArrayList( newText,  category);
                }
            }
        });
        findViewById(R.id.btnFindId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtDisease=findViewById(R.id.txtPaID);
                String newText=txtDisease.getText().toString();
                String category="id";

                if(newText.isEmpty()){
                    Toast.makeText(ManagePatient.this, "Invalid value entered", Toast.LENGTH_SHORT).show();
                }else {
                    searchArrayList( newText,  category);
                }
            }
        });
    }

    private void searchArrayList(String newText, String category) {
        ArrayList<PatientModel> dataSearchArrayList = new ArrayList<>();

        if (category.equals("disease")) {
            for (PatientModel data : patientModels) {

                //String name = data.getFname() + " " + data.getLname();
                if (data.getAddress().toLowerCase().contains(newText.toLowerCase())) {
                    dataSearchArrayList.add(data);
                }
            }

            if (dataSearchArrayList.isEmpty()) {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            } else {
                listPatientAdapters.setSearchList(dataSearchArrayList);
            }
        } else if (category.equals("id")) {
            for (PatientModel data : patientModels) {

                if (data.getNid().equals(newText)) {
                    dataSearchArrayList.add(data);
                }
            }

            if (dataSearchArrayList.isEmpty()) {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            } else {
                listPatientAdapters.setSearchList(dataSearchArrayList);
            }
        }

    }
}