package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatDrPatientAdapter extends RecyclerView.Adapter<ChatDrPatientAdapter.MyViewHolder> {

    final Context context;
    final ArrayList<ChatModel> chatModels;

    public ChatDrPatientAdapter(Context context, ArrayList<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public ChatDrPatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_dr_row2,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatDrPatientAdapter.MyViewHolder holder, int position) {


        ChatModel currentItem=chatModels.get(position);

        Toast.makeText(context, "Reached: "+currentItem.getFrom(), Toast.LENGTH_SHORT).show();

       holder.tvPatientName.setText(currentItem.getName());
        holder.tvLastMessage.setText(currentItem.getMessage());
        holder.tvDateTime.setText(currentItem.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,Chatting2.class);
                intent.putExtra("from",currentItem.getFrom());
                intent.putExtra("to",currentItem.getTo());
                context.startActivity(intent);
            }
        });

        /*holder.tvPatientName.setText("currentItem.getFrom()");
        holder.tvLastMessage.setText("currentItem.getMessage()");
        holder.tvDateTime.setText("currentItem.getDate()");

         */
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName,tvLastMessage,tvDateTime;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName=itemView.findViewById(R.id.txtName31);
            tvLastMessage=itemView.findViewById(R.id.txtLastMessage31);
            tvDateTime=itemView.findViewById(R.id.txtTime31);
            cardView=itemView.findViewById(R.id.cardView);


        }
    }
}
