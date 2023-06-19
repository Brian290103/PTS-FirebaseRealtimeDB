package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

    public class ChattingAdapter extends RecyclerView.Adapter< ChattingAdapter.MyViewHolder> {
        Context context;
        ArrayList<ChatModel> chatModel;
        private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();


        public void setSearchList(ArrayList<ChatModel> chatModel) {
            this.chatModel = chatModel;
            notifyDataSetChanged();
        }

        public ChattingAdapter(Context context, ArrayList<ChatModel> chatModel) {
            this.context = context;
            this.chatModel = chatModel;

            //Toast.makeText(context, "Size Sent: "+chatModel.size(), Toast.LENGTH_SHORT).show();
        }

        @NonNull
        @Override
        public  ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //This is where you inflate the layout (Giving a loom to our rows)

            View view = LayoutInflater.from(context).inflate(R.layout.chatting_row, parent, false);

            return new  ChattingAdapter.MyViewHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull  ChattingAdapter.MyViewHolder holder, int position) {
            // Assigning values to the views we created in the recycler_view_row layout
            //based on the position of hte recycler view

            holder.tvMessage.setText(chatModel.get(position).getMessage());
            holder.tvTime.setText(chatModel.get(position).getDate());

        }


        @Override
        public int getItemCount() {
            // The Recycler view just wants to know how many number of items you want to be displayed
            //Toast.makeText(context, "Items Count: "+chatModel.size(), Toast.LENGTH_SHORT).show();

            return chatModel.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            //Grabbing the views from our recycler_view_row layout file
            //Kinda like in the onCreate Method

            TextView tvMessage, tvTime;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvMessage=itemView.findViewById(R.id.txtchattingMessage);
                tvTime=itemView.findViewById(R.id.txtChattingMessgeTime);

            }

        }


    }


