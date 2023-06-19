package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {

    final ArrayList<ReportsModel> reportsModels;
    final Context context;

    public ReportsAdapter(ArrayList<ReportsModel> reportsModels, Context context) {
        this.reportsModels = reportsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_reports_row,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsAdapter.MyViewHolder holder, int position) {

        ReportsModel currentItem=reportsModels.get(position);
        holder.tvIndex.setText(position+1+"");
        holder.tvName.setText(currentItem.getPatient_id());
        holder.tvId.setText(currentItem.getPatient_id());
        holder.tvSymptoms.setText(currentItem.getR_symptoms());
        holder.tvComments.setText(currentItem.getR_comments());
        holder.tvPossibleDisease.setText(currentItem.getR_possible_disease());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(context,SingleMedicalReport.class);
                intent.putExtra("name",SharedPrefManager.getInstance(context).getUsername());
                intent.putExtra("idNo",currentItem.getPatient_id());
                intent.putExtra("phone",SharedPrefManager.getInstance(context).getPhone());
                intent.putExtra("diagnosis",currentItem.getR_possible_disease());
                intent.putExtra("doctorName",currentItem.getDoctor_id());
                intent.putExtra("date",currentItem.getDate());
                intent.putExtra("symptoms",currentItem.getR_symptoms());
                intent.putExtra("recommendations",currentItem.getR_comments());
                intent.putExtra("comments",currentItem.getR_comments());
                intent.putExtra("drName",currentItem.getDoctor_id());
                intent.putExtra("faculty",currentItem.getFaculty());


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvIndex, tvName,tvId,txGender,tvPhone,tvPossibleDisease,tvSymptoms,tvComments;
        RelativeLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex=itemView.findViewById(R.id.tvId);
            tvName=itemView.findViewById(R.id.tvName);
            tvId=itemView.findViewById(R.id.tvIdNo);
            txGender=itemView.findViewById(R.id.txGender);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            tvPossibleDisease=itemView.findViewById(R.id.tvPossibleDisease);
            tvSymptoms=itemView.findViewById(R.id.tvSymptoms);
            tvComments=itemView.findViewById(R.id.tvComments);
            row=itemView.findViewById(R.id.row);

        }
    }
}
