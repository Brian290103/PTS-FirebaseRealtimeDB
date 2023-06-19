package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

public class BookDoctor extends AppCompatActivity {

    private String specialityId;
    private TextView txt_specialityName;
    ProgressDialog progressDialog;
    DoctorAdapter doctorAdapter;
    RecyclerView recyclerView;

    private SearchView searchView;

    private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
    private ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    String facultyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_specialityName = findViewById(R.id.txt_speciality_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            specialityId = bundle.getString("ID");
            facultyName=bundle.getString("Name");
            txt_specialityName.setText(facultyName);
        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookDoctor.this));

        Query query=databaseReference.orderByChild("faculty").equalTo(facultyName);

       query.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               doctorModels.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   //Doctor doctor = dataSnapshot.getValue(Doctor.class);
                  // doctorList.add(doctor);
                   //Toast.makeText(BookDoctor.this, "Getting Doctors", Toast.LENGTH_SHORT).show();
                   String getId=dataSnapshot.child("nid").getValue(String.class);
                   String getPhone=dataSnapshot.child("phone").getValue(String.class);
                   String getFaculty=dataSnapshot.child("faculty").getValue(String.class);
                   int getIsPresent=dataSnapshot.child("isPresent").getValue(Integer.class);
                   String getFName=dataSnapshot.child("fname").getValue(String.class);
                   String getLName=dataSnapshot.child("lname").getValue(String.class);
                   String getGender=dataSnapshot.child("gender").getValue(String.class);
                   String getEmail=dataSnapshot.child("email").getValue(String.class);
                   String getAddress=dataSnapshot.child("address").getValue(String.class);
                   String getAvailableTimes=dataSnapshot.child("availableTimes").getValue(String.class);
                   String getDate=dataSnapshot.child("date").getValue(String.class);

                   //DoctorModel data = new DoctorModel(Integer.parseInt(getId),Integer.parseInt(getPhone),getFaculty,Integer.parseInt(getFaculty),"getFName","getLName","getGender","getEmail","getAddress","getAvailableTimes","getDate");
                   DoctorModel data = new DoctorModel(Integer.parseInt(getId),getPhone,getFaculty,getIsPresent,getFName,getLName,getGender,getEmail,getAddress,getAvailableTimes,getDate);
                  // DoctorModel data = new DoctorModel(Integer.parseInt(getId),Integer.parseInt(getPhone),getFaculty,Integer.parseInt(getIsPresent),getFName,getLName,getGender,getEmail,getAddress,getAvailableTimes,getDate);
                   doctorModels.add(data);
               }

             doctorAdapter=  new DoctorAdapter(BookDoctor.this, doctorModels);
               recyclerView.setAdapter(doctorAdapter);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
      /*  databaseReference.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(BookDoctor.this, "Generating Doctors", Toast.LENGTH_SHORT).show();

                        DataSnapshot dataSnapshot=task.getResult();
                        String getFName=dataSnapshot.child("fname").getValue(String.class);

                        //Set Text Now

                    }else{
                        Toast.makeText(BookDoctor.this, "Doctors Don't Exist Currently", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    */

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchArrayList(newText);
                return false;
            }
        });
    }

    private void searchArrayList(String newText) {
        ArrayList<DoctorModel> dataSearchArrayList = new ArrayList<>();

        for (DoctorModel data : doctorModels) {
            String name=data.getFname()+" "+data.getLname();

            if (name.toLowerCase().contains(newText.toLowerCase())) {
                dataSearchArrayList.add(data);
            }
        }

        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            doctorAdapter.setSearchList(dataSearchArrayList);
        }
    }
}