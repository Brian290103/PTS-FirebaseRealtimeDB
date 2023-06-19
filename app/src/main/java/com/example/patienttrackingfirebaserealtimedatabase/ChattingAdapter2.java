package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChattingAdapter2 extends RecyclerView.Adapter< ChattingAdapter2.MyViewHolder> {
    Context context;
    ArrayList<ChatModel> chatModel;
    private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();


    public void setSearchList(ArrayList<ChatModel> chatModel) {
        this.chatModel = chatModel;
        notifyDataSetChanged();
    }


    public ChattingAdapter2(Context context, ArrayList<ChatModel> chatModel) {
        this.context = context;
        this.chatModel = chatModel;

        //Toast.makeText(context, "Size Sent: "+chatModel.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public  ChattingAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a loom to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.chatting_row2, parent, false);

        return new  ChattingAdapter2.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull  ChattingAdapter2.MyViewHolder holder, int position) {
        // Assigning values to the views we created in the recycler_view_row layout
        //based on the position of hte recycler view

        ChatModel currentItem=chatModel.get(position);



        if(currentItem.getFrom().equals(Integer.toString(SharedPrefManager.getInstance(context).getUserId()))){
          //  Toast.makeText(context, "Its me", Toast.LENGTH_SHORT).show();
            holder.tvMessage.setText(currentItem.getMessage());
            holder.tvTime.setText(currentItem.getDate());

            holder.tvMessage2.setVisibility(View.GONE);
            holder.tvTime2.setVisibility(View.GONE);


        }else{
            holder.tvMessage2.setText(currentItem.getMessage());
            holder.tvTime2.setText(currentItem.getDate());

            holder.tvMessage.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.GONE);
        }

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
        TextView tvMessage2, tvTime2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage=itemView.findViewById(R.id.txtchattingMessage);
            tvTime=itemView.findViewById(R.id.txtChattingMessgeTime);

            tvMessage2=itemView.findViewById(R.id.txtchattingMessage2);
            tvTime2=itemView.findViewById(R.id.txtChattingMessgeTime2);

        }

    }


}


