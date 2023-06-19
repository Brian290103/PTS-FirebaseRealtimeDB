package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Chatting extends AppCompatActivity {

    TextView txtDoctorName;
    String doctorId;
    String date;
    TextView txtMessage;

    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
    ArrayList<ChatModel> chatModels = new ArrayList<>();
    RecyclerView recyclerView;
    Bundle bundle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        recyclerView = findViewById(R.id.chattingRecyclerView);
        //databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(Chatting.this));

        progressDialog = new ProgressDialog(Chatting.this);
        progressDialog.setMessage("Getting Messages...");
        progressDialog.show();

        retrievePastMessages();


        findViewById(R.id.btn_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDoctorName = findViewById(R.id.txtDoctorName);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            //Toast.makeText(this, "reached", Toast.LENGTH_SHORT).show();
            doctorId = bundle.getString("id");
            txtDoctorName.setText("Dr. " + bundle.getString("fname") + " " + bundle.getString("lname"));
        }

        txtMessage = findViewById(R.id.txtEnterMessage);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        final String message = txtMessage.getText().toString();

                        if (message.isEmpty()) {
                            Toast.makeText(Chatting.this, "Invalid message Entered", Toast.LENGTH_SHORT).show();
                        } else {


                            progressDialog.show();

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


                            final String id = databaseReference.push().getKey();
                            //doctorId;
                            final String from = Integer.toString(SharedPrefManager.getInstance(Chatting.this).getUserId());
                            //date

                            //final String patient_id=Integer.toString(SharedPrefManager.getInstance(context).getUserId());

                            databaseReference.child("messages").child(id).child("id").setValue(id);
                            databaseReference.child("messages").child(id).child("to").setValue(bundle.getString("id"));
                            databaseReference.child("messages").child(id).child("from").setValue(from);
                            databaseReference.child("messages").child(id).child("message").setValue(message);
                            databaseReference.child("messages").child(id).child("date").setValue(date);


                            txtMessage.setText("");
                            Toast.makeText(Chatting.this, "Sent", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                        progressDialog.dismiss();
                    }
                });


            }
        });


    }

    private void retrievePastMessages() {

        //  Query query = reference.orderByChild("from").equalTo(Integer.toString(SharedPrefManager.getInstance(Chatting.this).getUserId()));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();

                chatModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //  if(dataSnapshot.hasChild("id")&&dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("desc")) {
                    final String getId = dataSnapshot.child("id").getValue(String.class);
                    final String getTo = dataSnapshot.child("to").getValue(String.class);
                    final String getMessage = dataSnapshot.child("message").getValue(String.class);
                    final String getDate = dataSnapshot.child("date").getValue(String.class);
                    final String getFrom = dataSnapshot.child("from").getValue(String.class);

                    String patientId = Integer.toString(SharedPrefManager.getInstance(Chatting.this).getUserId());

                    if (getFrom.equals(patientId) && getTo.equals(doctorId) || getTo.equals(patientId) && getFrom.equals(doctorId)) {
                        ChatModel data = new ChatModel(getId, getFrom, getTo, getMessage, getDate, "");
                        //ChatModel data = new ChatModel("getId", "getFrom", "getTo", "getMessage", "getDate");
                        chatModels.add(data);
                    }
                }

                Collections.reverse(chatModels);
                recyclerView.setAdapter(new ChattingAdapter2(Chatting.this, chatModels));


                // NotificationAdapter.notifyDataSetChanged();
                // Toast.makeText(MakeAppointment.this, "Loading...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                progressDialog.dismiss();
            }
        });


    }
}