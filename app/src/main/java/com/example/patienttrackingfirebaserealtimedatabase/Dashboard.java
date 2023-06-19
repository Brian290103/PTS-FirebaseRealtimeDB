package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    private LinearLayout homeLayout, paymentLayout, supportLayout, chatDrLayout;
    private ImageView homeImage, paymentImage, supportImage, chatDrImage;
    private TextView homeTxt, paymentTxt, supportTxt, chatDrTxt;
    private int selectedTab = 1;

    private ImageView btn_notifications;
    private  TextView notificationBadge;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu_drawer;

    ImageView logout_btn;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");

    boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }



       notificationsPresent();

        btn_notifications = findViewById(R.id.btn_notifications);
        btn_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Notifications.class));

            }
        });



        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(Dashboard.this).logOut();
                Toast.makeText(Dashboard.this, "User Logged Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        menu_drawer = findViewById(R.id.imageView2);

        menu_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        homeLayout = findViewById(R.id.homeLayout);
        paymentLayout = findViewById(R.id.paymentLayout);
        supportLayout = findViewById(R.id.supportLayout);
        chatDrLayout = findViewById(R.id.chatDrLayout);

        homeImage = findViewById(R.id.homeImage);
        paymentImage = findViewById(R.id.paymentImage);
        supportImage = findViewById(R.id.supportImage);
        chatDrImage = findViewById(R.id.chatDrImage);

        homeTxt = findViewById(R.id.homeTxt);
        paymentTxt = findViewById(R.id.paymentTxt);
        supportTxt = findViewById(R.id.supportTxt);
        chatDrTxt = findViewById(R.id.chatDrTxt);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedTab != 1) {
                    renderHome();
                }

            }
        });
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2) {
                    //Replace the fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, PaymentFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    paymentTxt.setVisibility(View.VISIBLE);
                    supportTxt.setVisibility(View.GONE);
                    chatDrTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.nav_home);
                    paymentImage.setImageResource(R.drawable.nav_payment_selected);
                    supportImage.setImageResource(R.drawable.nav_support);
                    chatDrImage.setImageResource(R.drawable.nav_chat_dr);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    paymentLayout.setBackgroundResource(R.drawable.nav_bg_round);
                    supportLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatDrLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    paymentLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }

            }
        });
        supportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3) {
                    //Replace the fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, SupportFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    paymentTxt.setVisibility(View.GONE);
                    supportTxt.setVisibility(View.VISIBLE);
                    chatDrTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.nav_home);
                    paymentImage.setImageResource(R.drawable.nav_payment);
                    supportImage.setImageResource(R.drawable.nav_support_selected);
                    chatDrImage.setImageResource(R.drawable.nav_chat_dr);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    paymentLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    supportLayout.setBackgroundResource(R.drawable.nav_bg_round);
                    chatDrLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    supportImage.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }

            }
        });
        chatDrLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 4) {

                    //Replace the fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    paymentTxt.setVisibility(View.GONE);
                    supportTxt.setVisibility(View.GONE);
                    chatDrTxt.setVisibility(View.VISIBLE);

                    homeImage.setImageResource(R.drawable.nav_home);
                    paymentImage.setImageResource(R.drawable.nav_payment);
                    supportImage.setImageResource(R.drawable.nav_support);
                    chatDrImage.setImageResource(R.drawable.nav_chat_dr_selected);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    paymentLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    supportLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatDrLayout.setBackgroundResource(R.drawable.nav_bg_round);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    chatDrLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;


                    startActivity(new Intent(Dashboard.this, ChatDr.class));

                }

            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.w("1", "onPostResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        renderHome();
        notificationsPresent();
    }



    private void renderHome() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();

        homeTxt.setVisibility(View.VISIBLE);
        paymentTxt.setVisibility(View.GONE);
        supportTxt.setVisibility(View.GONE);
        chatDrTxt.setVisibility(View.GONE);

        homeImage.setImageResource(R.drawable.nav_home_selected);
        paymentImage.setImageResource(R.drawable.nav_payment);
        supportImage.setImageResource(R.drawable.nav_support);
        chatDrImage.setImageResource(R.drawable.nav_chat_dr);

        homeLayout.setBackgroundResource(R.drawable.nav_bg_round);
        paymentLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        supportLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        chatDrLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //create animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        homeLayout.startAnimation(scaleAnimation);

        selectedTab = 1;
    }

    private void notificationsPresent() {

       // btn_notifications.setImageResource(R.drawable.baseline_notifications_active_24);
        notificationBadge=findViewById(R.id.notification_badge );
        //notificationBadge.setVisibility(View.VISIBLE);


       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int unreadCount = 0;
                for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                    String notificationPatientId = notificationSnapshot.child("patient_id").getValue(String.class);
                   // Log.w("notificationPatientId", "Id: "+ notificationPatientId+"");

                    String getIsRead=notificationSnapshot.child("isRead").getValue(String.class);
                    //Log.w("notificationPatientId", "True: "+getIsRead);
                    if(getIsRead.equals("0")&&notificationPatientId.equals(Integer.toString(SharedPrefManager.getInstance(Dashboard.this).getUserId()))){
                        unreadCount++;
                        notificationBadge.setVisibility(View.VISIBLE);
                       // Log.w("notificationPatientId", "True ");
                    }

                }

                if (unreadCount > 0) {
                   // notificationBadge.setText(String.valueOf(unreadCount));
                    notificationBadge.setVisibility(View.VISIBLE);
                } else {
                    notificationBadge.setVisibility(View.GONE);
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
