package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorChatDr extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private ArrayList<ChatModel> chatModels = new ArrayList<>();
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat_dr);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorChatDr.this));

        progressDialog = new ProgressDialog(DoctorChatDr.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        loadRecentChats();
    }

    private void loadRecentChats() {

        // DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");

        // Query query = reference.orderByChild("role").equalTo("patient");

        reference.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                chatModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String getId = dataSnapshot.child("id").getValue(String.class);
                    String getDate = dataSnapshot.child("date").getValue(String.class);
                   String  getFrom = dataSnapshot.child("from").getValue(String.class);
                    String getMessage = dataSnapshot.child("message").getValue(String.class);
                    String getTo = dataSnapshot.child("to").getValue(String.class);

                    String userId = Integer.toString(SharedPrefManager.getInstance(DoctorChatDr.this).getUserId());

                 //   if (getFrom.equals(userId) || getTo.equals(userId)) {
                    if (getFrom != null && getFrom.equals(userId) || getTo != null && getTo.equals(userId)) {

                        //if (getFrom != null && getFrom.equals(userId) || getTo != null && getTo.equals(userId)) {
                        //    // Your code here
                        //}

                        if(getTo.equals(userId)){
                            FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    //  appointmentsModels.clear();
                                    progressDialog.dismiss();

                                    if (snapshot.hasChild(getFrom)) {
                                        // Toast.makeText(ViewMyReports.this, "patient Id gotten: "+ snapshot.child(getDoctorId).child("fname").getValue().toString(), Toast.LENGTH_SHORT).show();
                                        String getFName = snapshot.child(getFrom).child("fname").getValue().toString();
                                        String getLName = snapshot.child(getFrom).child("lname").getValue().toString();
                                        //String getFaculty = snapshot.child(getFrom).child("faculty").getValue().toString();

                                        String getName = getFName + " " + getLName;

                                        Toast.makeText(DoctorChatDr.this, "Name: " + getName, Toast.LENGTH_SHORT).show();

                                        ChatModel data = new ChatModel(getId, getFrom, getTo, getMessage, getDate,getName);
                                        chatModels.add(data);
                                        recyclerView.setAdapter(new ChatDrPatientAdapter(DoctorChatDr.this, chatModels));


                                    } else {
                                        progressDialog.dismiss();
                                        //Toast.makeText(NewAppointments.this, "patient Id not gotten", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                                    progressDialog.dismiss();

                                }
                            });

                        }



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


       /* ChatModel data = new ChatModel("1", "PatientID", "DoctorId", "hello Doctor", "current");
        chatModels.add(data);
        data = new ChatModel("1", "PatientID", "DoctorId", "hello Doctor", "current");
        chatModels.add(data);

        recyclerView.setAdapter(new ChatDrPatientAdapter(DoctorChatDr.this, chatModels));


        */

    }
}