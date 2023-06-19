package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ChatDr extends AppCompatActivity {

    ArrayList<ChatModel> chatModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dr);

        findViewById(R.id.select_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatDr.this, SelectChatDr.class));
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatDr.this));

       /* ChatModel data = new ChatModel("1", "Antony Luttah", "Dr. Maxwell Otieno", "Hello Doctor can we meet today afternoon", "2021-09-98 09:67:45");
        chatModels.add(data);
        data = new ChatModel("1", "Antony Luttah", "Dr. Maxwell Otieno", "Hello Doctor can we meet today afternoon", "2021-09-98 09:67:45");
        chatModels.add(data);
        data = new ChatModel("1", "Antony Luttah", "Dr. Maxwell Otieno", "Hello Doctor can we meet today afternoon", "2021-09-98 09:67:45");
        chatModels.add(data);*/
        // Log.w("MyError ","Error "+chatModels.size());

        recyclerView.setAdapter(new ChatAdapter(ChatDr.this, chatModels));


    }
}