package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakeAppointment extends AppCompatActivity {

    ImageView btn_back;
    private SearchView searchView;

    private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private ArrayList<SpecialtyModel> specialtyModels = new ArrayList<>();

    SpecialtyAdapter specialtyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MakeAppointment.this));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                specialtyModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.child("faculty").getChildren()) {

                    //  if(dataSnapshot.hasChild("id")&&dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("desc")) {

                        final String getId = dataSnapshot.child("id").getValue(String.class);
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getDesc = dataSnapshot.child("desc").getValue(String.class);


                      //  SpecialtyModel data = new SpecialtyModel(Integer.parseInt(getId), getName, getDesc);
                        SpecialtyModel data = new SpecialtyModel(getId, getName, getDesc);
                        specialtyModels.add(data);
                   // Toast.makeText(MakeAppointment.this, "Id: ", Toast.LENGTH_SHORT).show();
                   /* }else{
                        Toast.makeText(MakeAppointment.this, "Error", Toast.LENGTH_SHORT).show();

                    }*/
                }

                specialtyAdapter=new SpecialtyAdapter(MakeAppointment.this,specialtyModels);
                recyclerView.setAdapter(specialtyAdapter);


               // specialtyAdapter.notifyDataSetChanged();
               // Toast.makeText(MakeAppointment.this, "Loading...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        ArrayList<SpecialtyModel> dataSearchArrayList = new ArrayList<>();

        for (SpecialtyModel data : specialtyModels) {
            if (data.getName().toLowerCase().contains(newText.toLowerCase())) {
                dataSearchArrayList.add(data);
            }
        }

        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            specialtyAdapter.setSearchList(dataSearchArrayList);
        }
    }

}