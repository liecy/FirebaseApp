package com.rahmi.loginapps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahmi.loginapps.databinding.ActivityChannelBinding;
import com.rahmi.loginapps.databinding.ActivityLoginBinding;
import com.rahmi.loginapps.model.Channel;

import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    ArrayList<String> channelArrayList;
    ArrayList<String> urlArrayList;
    private ActivityChannelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChannelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("channel");
        channelArrayList = new ArrayList<>();
        urlArrayList = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String channel = ds.getValue(Channel.class).getNama();
                    String url = ds.getValue(Channel.class).getUrl();
                    channelArrayList.add(channel);
                    urlArrayList.add(url);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ChannelActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, channelArrayList);

                binding.listView.setAdapter(adapter);

                binding.listView.setOnItemClickListener((parent, view, position, id) -> {
                    String itemValue = channelArrayList.get(position);
                    String urlValue = urlArrayList.get(position);
                    Toast.makeText(ChannelActivity.this,
                            "Channel" + itemValue, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChannelActivity.this, VideoActivity.class);
                    intent.putExtra("url", urlValue);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,error.getMessage());
            }
        });
    }
}