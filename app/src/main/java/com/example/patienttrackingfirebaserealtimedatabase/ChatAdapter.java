package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatModel> chatModel;
    private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();


    public void setSearchList(ArrayList<ChatModel> chatModel) {
        this.chatModel = chatModel;
        notifyDataSetChanged();
    }


    public ChatAdapter(Context context, ArrayList<ChatModel> chatModel) {
        this.context = context;
        this.chatModel = chatModel;

        //Toast.makeText(context, "Size Sent: "+chatModel.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a loom to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_dr_row, parent, false);

        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Assigning values to the views we created in the recycler_view_row layout
        //based on the position of hte recycler view

      // holder.tvId.setText(position+1+"");
        holder.tvName.setText(chatModel.get(position).getFrom());
        holder.tvLastMessage.setText(chatModel.get(position).getMessage());
        holder.tvTime.setText(chatModel.get(position).getDate());
        holder.tvTime.setVisibility(View.VISIBLE);



       /* holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BookDoctor.class);

                intent.putExtra("ID",chatModel.get(holder.getAdapterPosition()).getId());
                intent.putExtra("Name",chatModel.get(holder.getAdapterPosition()).getName());

                context.startActivity(intent);
                

            }
        });
        */

        

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

        TextView tvName, tvLastMessage,tvTime,badge;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            badge=itemView.findViewById(R.id.badge);
            tvName=itemView.findViewById(R.id.txtName);
            tvTime=itemView.findViewById(R.id.txtTime);
            tvLastMessage=itemView.findViewById(R.id.txtLastMessage);



        }


    }


}
