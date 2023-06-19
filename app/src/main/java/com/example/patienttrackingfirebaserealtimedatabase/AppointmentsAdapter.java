package com.example.patienttrackingfirebaserealtimedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentsModel> appointmentsModels;

    String notificationId;
    ProgressDialog progressDialog, progressDialog1;

    AlertDialog dialog;
    String date1;


    public void setSearchList(ArrayList<AppointmentsModel> appointmentsModels) {
        this.appointmentsModels = appointmentsModels;
        notifyDataSetChanged();
    }

    public AppointmentsAdapter(Context context, ArrayList<AppointmentsModel> appointmentsModels) {
        this.context = context;
        this.appointmentsModels = appointmentsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_appointments_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppointmentsModel currentItem = appointmentsModels.get(position);

        notificationId = appointmentsModels.get(position).getId() + "";
        String date = appointmentsModels.get(position).getAppDate();
        String time = appointmentsModels.get(position).getAppTime();

        holder.tvIndex.setText(position + 1 + "");
        holder.tvPatientName.setText(appointmentsModels.get(position).getPatientName());
        holder.tvAppDateTime.setText(date + " " + time);

        if(currentItem.getIsApproved().equals("0")){
            holder.constraintLayout.setBackgroundResource(R.drawable.appointments_bg_red);
        }


        holder.btnViewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                View view = LayoutInflater.from(context).inflate(R.layout.activity_view_appointment, null);

                TextView tvPatientName = view.findViewById(R.id.tvPatientName);
                TextView tvAppDateTime = view.findViewById(R.id.tvAppDateTime);
                TextView tvDesc = view.findViewById(R.id.tvDesc);

                tvPatientName.setText(currentItem.getPatientName());
                tvAppDateTime.setText(currentItem.getAppDate() + " " + currentItem.getAppTime());
                tvDesc.setText(currentItem.getMessage());

                Button btnDecline = view.findViewById(R.id.btnDecline);
                Button btnApprove = view.findViewById(R.id.btnApprove);

                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.doctor_comment, null);

                        TextView txt_drComments = view.findViewById(R.id.drComments);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

                                        if (txt_drComments.getText().toString().equals("")) {
                                            Toast.makeText(context, "Aborted, Provide an Appropriate Message", Toast.LENGTH_SHORT).show();
                                        } else {


                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    final String id2 = databaseReference.push().getKey();

                                                    DateTimeFormatter dtf = null;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                                    }
                                                    LocalDateTime now = null;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        now = LocalDateTime.now();
                                                    }
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        date1 = dtf.format(now);
                                                    }

                                                    databaseReference.child("notifications").child(id2).child("id").setValue(id2);
                                                    databaseReference.child("notifications").child(id2).child("patient_id").setValue(currentItem.getPatientId());
                                                    databaseReference.child("notifications").child(id2).child("reason").setValue("Appointment has been declined");
                                                    databaseReference.child("notifications").child(id2).child("isRead").setValue("0");
                                                    databaseReference.child("notifications").child(id2).child("date").setValue(date1);

                                                  //  context.startActivity(new Intent(context,Dashboard.class));

                                                }


                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                          /*  databaseReference.child("date").setValue(date1);
                                            databaseReference.child("id").setValue(id);
                                            databaseReference.child("isRead").setValue("0");
                                            databaseReference.child("patient_id").setValue(currentItem.getPatientId());
                                            databaseReference.child("reason").setValue("Your Appointment has been decline... "+ currentItem.getMessage());



                                           */
                                            Toast.makeText(context, "Declining...", Toast.LENGTH_SHORT).show();


                                            context.startActivity(new Intent(context, DashboardDr.class));

                                            dialog.cancel();
                                        }
                                    }
                                })
                                .setView(view);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Approving...", Toast.LENGTH_SHORT).show();


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appointments");

                        reference.child(currentItem.getId()).child("isApproved").setValue("1");

                        Toast.makeText(context, "Appointment has been approved", Toast.LENGTH_SHORT).show();

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    final String id2 = databaseReference.push().getKey();

                                    DateTimeFormatter dtf = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    }
                                    LocalDateTime now = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        now = LocalDateTime.now();
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        date1 = dtf.format(now);
                                    }

                                    databaseReference.child("notifications").child(id2).child("id").setValue(id2);
                                    databaseReference.child("notifications").child(id2).child("patient_id").setValue(currentItem.getPatientId());
                                    databaseReference.child("notifications").child(id2).child("reason").setValue("Appointment has been approved on: "+currentItem.getAppDate()+" at "+currentItem.getAppTime());
                                    databaseReference.child("notifications").child(id2).child("isRead").setValue("0");
                                    databaseReference.child("notifications").child(id2).child("date").setValue(date1);

                                    //  context.startActivity(new Intent(context,Dashboard.class));

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            context.startActivity(new Intent(context, DashboardDr.class));


                        dialog.cancel();

                    }
                });


                builder.setView(view);

                dialog = builder.create();
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return appointmentsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPatientName;
        TextView tvIndex;
        TextView tvAppDateTime;
        ImageView btnViewApp;
        ConstraintLayout constraintLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvIndex = itemView.findViewById(R.id.tv_index);
            tvAppDateTime = itemView.findViewById(R.id.tv_appdate_time);
            btnViewApp = itemView.findViewById(R.id.btnViewApp);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);


        }
    }
}

