package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {
    final Context context;
    final ArrayList<PatientModel> patientModels;

    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");

    public PatientAdapter(Context context, ArrayList<PatientModel> patientModels) {
        this.context = context;
        this.patientModels = patientModels;
    }


    @NonNull
    @Override
    public PatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.patients_not_approved_row, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.MyViewHolder holder, int position) {
        PatientModel currentItem = patientModels.get(position);

        holder.indexTv.setText(position + 1 + "");
        holder.nameTv.setText(currentItem.getFname() + " " + currentItem.getLname());
        holder.idNoTv.setText(currentItem.getNid());
        holder.addressTv.setText(currentItem.getAddress());

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ManagePatient.class));
                Toast.makeText(context, "Declined", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // context.startActivity(new Intent(context, ManagePatient.class));
                Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
                databaseReference.child(currentItem.getNid()).child("isApproved").setValue(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView indexTv, nameTv, idNoTv, addressTv;
        ImageView btnDecline, btnApprove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            indexTv = itemView.findViewById(R.id.indexTV);
            nameTv = itemView.findViewById(R.id.tvPatientName);
            idNoTv = itemView.findViewById(R.id.tvPatientID);
            addressTv = itemView.findViewById(R.id.tvPatientAddress);

            btnDecline = itemView.findViewById(R.id.btnDeclinePatient);
            btnApprove = itemView.findViewById(R.id.btnApprovePatient);
        }
    }
}
