package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardAdmin extends AppCompatActivity {
    private TextView notificationBadge;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        findViewById(R.id.manageProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardAdmin.this, ManageProfile.class);

                intent.putExtra("view", "admin");

                startActivity(intent);

            }
        });

        notificationsPresent();

        findViewById(R.id.btn_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), NotificationsAdmin.class));
            }
        });
        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(DashboardAdmin.this).logOut();
                Toast.makeText(DashboardAdmin.this, "Admin Logged Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        findViewById(R.id.logout_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(DashboardAdmin.this).logOut();
                Toast.makeText(DashboardAdmin.this, "Admin Logged Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


        findViewById(R.id.btn_addPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdmin.this, ManageDoctor.class));
            }
        });
        findViewById(R.id.btnViewMyReports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdmin.this, ManagePatient.class));
            }
        });
    }

    private void notificationsPresent() {

        // btn_notifications.setImageResource(R.drawable.baseline_notifications_active_24);
        notificationBadge = findViewById(R.id.notification_badge);
        //notificationBadge.setVisibility(View.VISIBLE);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int unreadCount = 0;
                for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                    String notificationPatientId = notificationSnapshot.child("patient_id").getValue(String.class);
                    // Log.w("notificationPatientId", "Id: "+ notificationPatientId+"");

                    String getIsRead = notificationSnapshot.child("isRead").getValue(String.class);
                    //Log.w("notificationPatientId", "True: "+getIsRead);
                    if (getIsRead.equals("0") && notificationPatientId.equals(Integer.toString(SharedPrefManager.getInstance(DashboardAdmin.this).getUserId()))) {
                        unreadCount++;
                        notificationBadge.setVisibility(View.VISIBLE);
                        // Log.w("notificationPatientId", "True ");
                    }

                }

                if (unreadCount > 0) {
                    // notificationBadge.setText(String.valueOf(unreadCount));
                    notificationBadge.setVisibility(View.VISIBLE);
                } else {
                    notificationBadge.setVisibility(View.VISIBLE);
                }

                //notificationBadge.setText(unreadCount);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "onCancelled", error.toException());
            }
        });

    }

}