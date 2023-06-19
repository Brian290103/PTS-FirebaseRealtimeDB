package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListDoctorsAdapters extends RecyclerView.Adapter<ListDoctorsAdapters.MyViewHolder> {
    final Context context;
     ArrayList<DoctorModel>doctorModels;


    public void setSearchList(ArrayList<DoctorModel> doctorModels) {
        this.doctorModels = doctorModels;
        notifyDataSetChanged();
    }

    public ListDoctorsAdapters(Context context, ArrayList<DoctorModel> doctorModels) {
        this.context = context;
        this.doctorModels = doctorModels;
    }

    @NonNull
    @Override
    public ListDoctorsAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.doctors_row,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ListDoctorsAdapters.MyViewHolder holder, int position) {

        DoctorModel currentItem=doctorModels.get(position);
        holder.indexTv.setText(position+1+"");
        holder.doctorNameTv.setText(currentItem.getFname()+" "+currentItem.getLname());
        holder.facultyTv.setText(currentItem.getFaculty());
        holder.doctorIdTv.setText(currentItem.getId()+"");
       // holder.
    }

    @Override
    public int getItemCount() {
        return doctorModels.size();
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
}
