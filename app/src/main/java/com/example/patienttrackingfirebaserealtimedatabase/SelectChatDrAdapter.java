package com.example.patienttrackingfirebaserealtimedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


    public class SelectChatDrAdapter extends RecyclerView.Adapter< SelectChatDrAdapter.MyViewHolder> {
        Context context;
        ArrayList<DoctorModel> doctorModel;
        private  final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();


        public void setSearchList(ArrayList< DoctorModel>  doctorModel) {
            this. doctorModel =  doctorModel;
            notifyDataSetChanged();
        }


        public SelectChatDrAdapter(Context context, ArrayList< DoctorModel>  doctorModel) {
            this.context = context;
            this. doctorModel =  doctorModel;

            //Toast.makeText(context, "Size Sent: "+ doctorModel.size(), Toast.LENGTH_SHORT).show();
        }

        @NonNull
        @Override
        public  SelectChatDrAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //This is where you inflate the layout (Giving a loom to our rows)

            View view = LayoutInflater.from(context).inflate(R.layout.chat_list_dr_row, parent, false);

            return new  SelectChatDrAdapter.MyViewHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull  SelectChatDrAdapter.MyViewHolder holder, int position) {
            // Assigning values to the views we created in the recycler_view_row layout
            //based on the position of hte recycler view

            // holder.tvId.setText(position+1+"");

            DoctorModel currentItem=doctorModel.get(position);

            String fname=doctorModel.get(position).getFname();
            String lname=doctorModel.get(position).getLname();

            holder.tvName.setText("Dr. "+fname+" "+lname);
            holder.tvLastMessage.setText( doctorModel.get(position).getFaculty());
            holder.tvTime.setVisibility(View.GONE);
            
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context,Chatting.class);

                    intent.putExtra("id", Integer.toString(currentItem.getId()));
                    intent.putExtra("fname", fname);
                    intent.putExtra("lname", lname);

                    context.startActivity(intent);
                }
            });

       /* holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BookDoctor.class);

                intent.putExtra("ID", doctorModel.get(holder.getAdapterPosition()).getId());
                intent.putExtra("Name", doctorModel.get(holder.getAdapterPosition()).getName());

                context.startActivity(intent);
                

            }
        });
        */



        }


        @Override
        public int getItemCount() {
            // The Recycler view just wants to know how many number of items you want to be displayed
            //Toast.makeText(context, "Items Count: "+ doctorModel.size(), Toast.LENGTH_SHORT).show();

            return  doctorModel.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            //Grabbing the views from our recycler_view_row layout file
            //Kinda like in the onCreate Method

            TextView tvName, tvLastMessage,tvTime,badge;
            CardView cardView;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                badge=itemView.findViewById(R.id.badge);
                tvName=itemView.findViewById(R.id.txtName);
                tvTime=itemView.findViewById(R.id.txtTime);
                tvLastMessage=itemView.findViewById(R.id.txtLastMessage);
                cardView=itemView.findViewById(R.id.cardView);



            }


        }


    }


