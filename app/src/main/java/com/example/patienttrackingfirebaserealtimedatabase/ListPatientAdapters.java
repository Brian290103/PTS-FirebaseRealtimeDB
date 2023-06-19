package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPatientAdapters extends  RecyclerView.Adapter<ListPatientAdapters.MyViewHolder>{
    final Context context;
     ArrayList<PatientModel> patientModels;


    public void setSearchList(ArrayList<PatientModel> patientModels) {
        this.patientModels = patientModels;
        notifyDataSetChanged();
    }


    public ListPatientAdapters(Context context, ArrayList<PatientModel> patientModels) {
        this.context = context;
        this.patientModels = patientModels;
    }

    @NonNull
    @Override
    public ListPatientAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctors_row,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ListPatientAdapters.MyViewHolder holder, int position) {

        PatientModel currentItem=patientModels.get(position);
        holder.tvIndex.setText(position+1+"");
        holder.tvName.setText(currentItem.getFname()+" "+currentItem.getLname());
        holder.tvId.setText(currentItem.getNid());
        holder.tvFaculty.setText(currentItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return patientModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvIndex,tvName,tvId,tvFaculty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex=itemView.findViewById(R.id.indexTV);
            tvName=itemView.findViewById(R.id.doctorName);
            tvId=itemView.findViewById(R.id.doctorId);
            tvFaculty=itemView.findViewById(R.id.doctorFaculty);
        }
    }

    /*final Context context;
     ArrayList<PatientModel>patientModels;


    public void setSearchList(ArrayList<PatientModel> patientModels) {
        this.patientModels = patientModels;
        notifyDataSetChanged();
    }

    public ListPatientAdapters(Context context, ArrayList<PatientModel> patientModels) {
        this.context = context;
        this.patientModels = patientModels;

    }

    @NonNull
    @Override
    public ListPatientAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctors_row,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ListPatientAdapters.MyViewHolder holder, int position) {

        PatientModel currentItem=patientModels.get(position);

       // Toast.makeText(context, "Size: "+currentItem.getLname(), Toast.LENGTH_SHORT).show();


        holder.indexTv.setText(position+1+"");
        holder.doctorNameTv.setText(currentItem.getFname()+" "+currentItem.getLname());
        //holder.facultyTv.setText(currentItem.getFaculty());
        //holder.doctorIdTv.setText(currentItem.getId()+"");
       // holder.
    }

    @Override
    public int getItemCount() {
        return patientModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView indexTv,doctorNameTv,facultyTv,doctorIdTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            indexTv=itemView.findViewById(R.id.indexTV);
            doctorNameTv=itemView.findViewById(R.id.doctorName);
            facultyTv=itemView.findViewById(R.id.doctorFaculty);
            doctorIdTv=itemView.findViewById(R.id.doctorId);
        }
    }

     */
}
