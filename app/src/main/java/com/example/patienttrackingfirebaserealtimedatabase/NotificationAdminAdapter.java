package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

    public class NotificationAdminAdapter extends RecyclerView.Adapter< NotificationAdminAdapter .MyViewHolder> {

        Context context;
        ArrayList<NotificationModel> notificationModels;

        private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");


        public void setSearchList(ArrayList<NotificationModel> notificationModels) {
            this.notificationModels = notificationModels;
            notifyDataSetChanged();
        }

        public NotificationAdminAdapter (Context context, ArrayList<NotificationModel> notificationModels) {
            this.context = context;
            this.notificationModels = notificationModels;
        }

        @NonNull
        @Override
        public  NotificationAdminAdapter .MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notifications_row, parent, false);

            return new  NotificationAdminAdapter .MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull  NotificationAdminAdapter .MyViewHolder holder, int position) {


            NotificationModel currentItem = notificationModels.get(position);

            holder.tvId.setText(position + 1 + "");
            holder.tvReason.setText(notificationModels.get(position).getReason());
            holder.tvTime.setText(notificationModels.get(position).date);

            //int colorRes = R.color.soft_green;

            if (notificationModels.get(position).isRead.equals("1")) {
                // holder.btn_markAsRead.setBackgroundColor(colorRes);
                holder.btn_markAsRead.setBackgroundColor(Color.parseColor("#FFA500"));
                holder.btn_markAsRead.setText("Read");
            }

            holder.btn_markAsRead.setVisibility(View.GONE);

            holder.btn_markAsRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Marked as Read", Toast.LENGTH_SHORT).show();

                    databaseReference.child(currentItem.getId()).child("isRead").setValue("1");

                }
            });
        }

        @Override
        public int getItemCount() {
            return notificationModels.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvId;
            TextView tvReason;
            TextView tvTime;
            Button btn_markAsRead;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvReason = itemView.findViewById(R.id.txt_reason);
                tvId = itemView.findViewById(R.id.txt_id);
                tvTime = itemView.findViewById(R.id.txt_notTime);
                btn_markAsRead = itemView.findViewById(R.id.btn_markAsRead);


            }
        }
    }
    
