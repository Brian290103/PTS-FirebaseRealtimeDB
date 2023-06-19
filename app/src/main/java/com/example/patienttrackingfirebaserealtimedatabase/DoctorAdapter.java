package com.example.patienttrackingfirebaserealtimedatabase;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> doctorModels;
    TextView select_date, select_time, txt_message;
    String doctor_id;
    String faculty;
    ProgressDialog progressDialog;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    AlertDialog alertDialog1;

    private final String CHANNEL_ID = "Notification";
    private final int NOTIFICATION_ID = 01;
    String date;

    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://patient-tracking-firebase-rtdb-default-rtdb.firebaseio.com/");


    public void setSearchList(ArrayList<DoctorModel> doctorModels) {
        this.doctorModels = doctorModels;
        notifyDataSetChanged();
    }

    public DoctorAdapter(Context context, ArrayList<DoctorModel> doctorModels) {
        this.context = context;
        this.doctorModels = doctorModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_doctor_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        String fname = doctorModels.get(position).getFname();
        String lname = doctorModels.get(position).getLname();
        doctor_id = Integer.toString(doctorModels.get(position).getId());
        faculty = doctorModels.get(position).getFaculty();

        holder.tvId.setText(position + 1 + "");
        holder.tvName.setText("Doctor " + fname + " " + lname);
        holder.txAvailableTimes.setText(doctorModels.get(position).getAvailable_times());

        if (doctorModels.get(position).getIsPresent() == 1) {
            holder.imgStatus.setImageResource(R.drawable.presnt_icon);
            //holder.btn_bookNow.setVisibility(View.VISIBLE);
            holder.btn_bookNow.setEnabled(true);
        } else if (doctorModels.get(position).getIsPresent() == 2) {
            holder.imgStatus.setImageResource(R.drawable.absent_icon);
        }

        holder.btn_bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Doctor Id.: "+doctorModels.get(position).getUser_id(), Toast.LENGTH_SHORT).show();

                builder = new AlertDialog.Builder(context);

                View view = LayoutInflater.from(context).inflate(R.layout.single_book_doctor, null);

                select_date = view.findViewById(R.id.txt_date);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DateListener(), year, month, day);
                datePickerDialog.setCancelable(true);
                select_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                select_time = view.findViewById(R.id.txt_time);

                Calendar calendar1 = Calendar.getInstance();
                int hour = calendar1.get(Calendar.HOUR_OF_DAY);
                int minute = calendar1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new DateListener(), hour, minute, false);
                timePickerDialog.setCancelable(true);
                select_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerDialog.show();
                    }
                });

                txt_message = view.findViewById(R.id.txt_message);

                progressDialog = new ProgressDialog(context);
                // Toast.makeText(context, "User ID: "+SharedPrefManager.getInstance(view.getContext()).getUserId(), Toast.LENGTH_SHORT).show();
                view.findViewById(R.id.btn_appoint).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final String id = databaseReference.push().getKey();
                                final String id2 = databaseReference.push().getKey();

                                //final String patient_id=Integer.toString(SharedPrefManager.getInstance(context).getUserId());
                                final String message = txt_message.getText().toString();
                                final String app_date = select_date.getText().toString();
                                final String app_time = select_time.getText().toString();

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

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                }

                           /*     String appDateTime=app_date+" "+app_time;

                                LocalDateTime dateTime1 = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dateTime1 = LocalDateTime.parse(date, formatter);
                                }
                                LocalDateTime dateTime2 = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dateTime2 = LocalDateTime.parse(dateTime2String, formatter);
                                }

                                Duration duration = Duration.between(dateTime1, dateTime2);

                                Duration duration = Duration.between(appDateTime, dateTime2);
*/
                                databaseReference.child("appointments").child(id).child("id").setValue(id);
                                databaseReference.child("appointments").child(id).child("patient_id").setValue(Integer.toString(SharedPrefManager.getInstance(context).getUserId()));
                                databaseReference.child("appointments").child(id).child("doctor_id").setValue(doctor_id);
                                databaseReference.child("appointments").child(id).child("faculty").setValue(faculty);
                                databaseReference.child("appointments").child(id).child("message").setValue(message);
                                databaseReference.child("appointments").child(id).child("app_date").setValue(app_date);
                                databaseReference.child("appointments").child(id).child("app_time").setValue(app_time);
                                databaseReference.child("appointments").child(id).child("isPaid").setValue("0");
                                databaseReference.child("appointments").child(id).child("isApproved").setValue("0");
                                databaseReference.child("appointments").child(id).child("date").setValue(date);

                                Toast.makeText(context, "Appointment Made Successfully", Toast.LENGTH_SHORT).show();

                                databaseReference.child("notifications").child(id2).child("id").setValue(id2);
                                databaseReference.child("notifications").child(id2).child("patient_id").setValue(Integer.toString(SharedPrefManager.getInstance(context).getUserId()));
                                databaseReference.child("notifications").child(id2).child("reason").setValue("Appointment has been made");
                                databaseReference.child("notifications").child(id2).child("isRead").setValue("0");
                                databaseReference.child("notifications").child(id2).child("date").setValue(date);

                                context.startActivity(new Intent(context,Dashboard.class));

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                       /* databaseReference.child("appointments").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                              final   String id2=databaseReference.push().getKey();

                                databaseReference.child("notifications").child(id2).child("id").setValue(id);
                                databaseReference.child("appointments").child(id2).child("patient_id").setValue(Integer.toString(SharedPrefManager.getInstance(context).getUserId()));
                                databaseReference.child("appointments").child(id2).child("reason").setValue("Appointment has been made");
                                databaseReference.child("appointments").child(id2).child("date").setValue(date);

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
*/

                    }
                });


                builder.setView(view);

                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void goToHome() {
        context.startActivity(new Intent(context, Dashboard.class));
    }


    @Override
    public int getItemCount() {
        return doctorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvName, txAvailableTimes;
        ImageView imgStatus;
        Button btn_bookNow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.txt_id);
            tvName = itemView.findViewById(R.id.txt_reason);
            txAvailableTimes = itemView.findViewById(R.id.txt_notTime);

            imgStatus = itemView.findViewById(R.id.img_status);
            btn_bookNow = itemView.findViewById(R.id.btn_markAsRead);
        }
    }

    public class DateListener implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            String date = year + "/" + month + "/" + dayOfMonth;
            select_date.setText(date);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = hourOfDay + ":" + minute;

            select_time.setText(time);
        }
    }
}
