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

public class Chatting2 extends AppCompatActivity {
    TextView txtPatientName;
    String doctorId;
    String date;
    TextView txtMessage, txtEnterMessage;

    String from, to;
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
    ArrayList<ChatModel> chatModels = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting2);


        bundle = getIntent().getExtras();


        if (!bundle.isEmpty()) {

            from = bundle.getString("from");
            to = bundle.getString("to");

            Toast.makeText(this, from, Toast.LENGTH_SHORT).show();

        }


        recyclerView = findViewById(R.id.chatting2RecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(Chatting2.this);
        progressDialog.setMessage("Getting Messages...");
        progressDialog.show();

        retrievePastMessages();


        txtEnterMessage = findViewById(R.id.txtEnterMessage);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageToSend = txtEnterMessage.getText().toString();

                if (messageToSend.isEmpty()) {
                    Toast.makeText(Chatting2.this, "Invalid message Entered", Toast.LENGTH_SHORT).show();
                } else {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("messages").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

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


                            final String id = reference.push().getKey();
                            //doctorId;
                            final String from = Integer.toString(SharedPrefManager.getInstance(Chatting2.this).getUserId());
                            // final String message = txtMessage.getText().toString();
                            //date

                            //final String patient_id=Integer.toString(SharedPrefManager.getInstance(context).getUserId());

                            databaseReference.child("messages").child(id).child("id").setValue(id);
                            databaseReference.child("messages").child(id).child("to").setValue(bundle.getString("from"));
                            databaseReference.child("messages").child(id).child("from").setValue(from);
                            databaseReference.child("messages").child(id).child("message").setValue(messageToSend);
                            databaseReference.child("messages").child(id).child("date").setValue(date);

                            txtMessage.setText("");
                            Toast.makeText(Chatting2.this, "Sent", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Log.w("ErrorOccured", "Error: " + error.getMessage());

                        }
                    });
                }
            }
        });


        findViewById(R.id.btn_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtPatientName = findViewById(R.id.txtPatientName23);


        txtMessage = findViewById(R.id.txtEnterMessage);


    }

    private void retrievePastMessages() {


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();

                chatModels.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    final String getId = dataSnapshot.child("id").getValue(String.class);
                    final String getDate = dataSnapshot.child("date").getValue(String.class);
                    final String getFrom = dataSnapshot.child("from").getValue(String.class);
                    final String getMessage = dataSnapshot.child("message").getValue(String.class);
                    final String getTo = dataSnapshot.child("to").getValue(String.class);

                    String userId = Integer.toString(SharedPrefManager.getInstance(Chatting2.this).getUserId());

                    // Log.w("MyData","Id: "+getId+" Date: "+getDate+" From: "+getFrom+" Message: "+getMessage+" To: "+getTo);


                    if (getFrom!=null && getTo != null && getFrom.equals(from) && getTo.equals(userId) || getTo.equals(from) && getFrom.equals(userId)) {


                        //  if(getTo.equals(userId)){
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

                                    // Toast.makeText(Chatting2.this, "Name: " + getName, Toast.LENGTH_SHORT).show();

                                    ChatModel data = new ChatModel(getId, getFrom, getTo, getMessage, getDate, getName);

                                    //recyclerView.setAdapter(new ChattingAdapter2(Chatting2.this, chatModels));

                                    //ChatModel data = new ChatModel("getId", "getFrom", "getTo", "getMessage", "getDate","getName");
                                    chatModels.add(data);
                                    Collections.reverse(chatModels);
                                    recyclerView.setAdapter(new ChattingAdapter2(Chatting2.this, chatModels));


                                } else {
                                    progressDialog.dismiss();
                                    // Toast.makeText(Chatting2.this, "patient Id not gotten", Toast.LENGTH_SHORT).show();
                                }

                                   /* ChatModel data = new ChatModel("getId", "getFrom", "getTo", "getMessage", "getDate","getName");
                                    chatModels.add(data);

                                    */


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.w("Tag", "MyErrorMessage: " + error.getMessage());
                                progressDialog.dismiss();

                            }
                        });

                        //  }


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });




     /*   Query query = reference.orderByChild("from").equalTo(Integer.toString(SharedPrefManager.getInstance(Chatting2.this).getUserId()));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                chatModels.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //  if(dataSnapshot.hasChild("id")&&dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("desc")) {
                    final String getId = dataSnapshot.child("id").getValue(String.class);
                    final String getTo = dataSnapshot.child("to").getValue(String.class);
                    final String getMessage = dataSnapshot.child("message").getValue(String.class);
                    final String getDate = dataSnapshot.child("date").getValue(String.class);
                    final String getFrom = dataSnapshot.child("from").getValue(String.class);


                    ChatModel data = new  ChatModel(getId,getFrom,getTo,getMessage,getDate,"");
                    //ChatModel data = new ChatModel("getId", "getFrom", "getTo", "getMessage", "getDate");
                    chatModels.add(data);

                }

                Collections.reverse(chatModels);
                recyclerView.setAdapter(new ChattingAdapter(Chatting2.this, chatModels));


                // NotificationAdapter.notifyDataSetChanged();
                // Toast.makeText(MakeAppointment.this, "Loading...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



      */
    }
}