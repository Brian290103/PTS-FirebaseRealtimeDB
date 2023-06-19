package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ManageProfile extends AppCompatActivity {

    private String view;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    TextView tvName, tvId, tvGender, tvEmail, tvPhone, tvAddress, tvPass, tvCPass;
    Switch aSwitch;
    String id = Integer.toString(SharedPrefManager.getInstance(ManageProfile.this).getUserId());
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        tvName = findViewById(R.id.tvName);
        tvId = findViewById(R.id.tvId);
        tvGender = findViewById(R.id.tvGender);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvPass = findViewById(R.id.tvPass);
        tvCPass = findViewById(R.id.tvCPass);
        aSwitch = findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Log.v("Switch State=", ""+isChecked);
                // Toast.makeText(ManageProfile.this, "Switch State= "+isChecked, Toast.LENGTH_SHORT).show();

                if (aSwitch.isChecked()) {
                    databaseReference.child(id).child("isPresent").setValue(1);
                } else {
                    databaseReference.child(id).child("isPresent").setValue(0);
                }
            }

        });


        progressDialog = new ProgressDialog(ManageProfile.this);
        progressDialog.setMessage("Getting data...");
        progressDialog.show();


        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.getString("view").equals("admin")) {
                view = "admin";
            } else if (bundle.getString("view").equals("doctor")) {
                view = "doctor";
            } else if (bundle.getString("view").equals("patient")) {
                view="patient";
            }

        }



        if (view.equals("admin")||view.equals("patient")) {
            findViewById(R.id.activate).setVisibility(View.GONE);
        }

        findViewById(R.id.btnUpdateMyRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tvPass.getText().toString().equals(tvCPass.getText().toString())) {
                    Toast.makeText(ManageProfile.this, "The two passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(id).child("pass").setValue(tvCPass.getText().toString());
                    Toast.makeText(ManageProfile.this, "Record Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.dismiss();
                if (snapshot.hasChild(Integer.toString(SharedPrefManager.getInstance(ManageProfile.this).getUserId()))) {

                    //Toast.makeText(ManageProfile.this, "Id Exists", Toast.LENGTH_SHORT).show();
                    final String getFName = snapshot.child(id).child("fname").getValue().toString();
                    final String getLName = snapshot.child(id).child("lname").getValue().toString();
                    final String getGender = snapshot.child(id).child("gender").getValue().toString();
                    final String getNId = snapshot.child(id).child("nid").getValue().toString();
                    final String getEmail = snapshot.child(id).child("email").getValue().toString();
                    final String getPhone = snapshot.child(id).child("phone").getValue().toString();
                    final String getAddress = snapshot.child(id).child("address").getValue().toString();
                    final String getPass = snapshot.child(id).child("pass").getValue().toString();

                    if(view.equals("doctor")){

                        final String getIsPresent = snapshot.child(id).child("isPresent").getValue().toString();

                        if (getIsPresent.equals("1")) {
                            aSwitch.setChecked(true);
                        } else if (getIsPresent.equals("0")) {
                            aSwitch.setChecked(false);
                        }
                    }


                    tvName.setText(getFName + " " + getLName);
                    tvGender.setText(getGender);
                    tvId.setText(getNId);
                    tvEmail.setText(getEmail);
                    tvPhone.setText(getPhone);
                    tvAddress.setText(getAddress);
                    tvPass.setText(getPass);

                }

                //  String id = txt_n_id.getText().toString();
                //  String pass = txt_pass.getText().toString();

                ////  String id=Integer.toString(SharedPrefManager.getInstance(ManageProfile.this).getUserId());


            }
            // }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

    }
}