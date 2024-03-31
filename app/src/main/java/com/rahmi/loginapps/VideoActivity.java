package com.rahmi.loginapps;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rahmi.loginapps.databinding.ActivityChannelBinding;
import com.rahmi.loginapps.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ActivityVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        playVideo();
        mVideoView.start();
    }

    private void playVideo() {
        mVideoView = binding.videoView;
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Log.d(TAG, "playVideo" + url);

        mVideoView.setVideoURI(Uri.parse(url));
        MediaController mediaController = new MediaController(VideoActivity.this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        playVideo();
        mVideoView.start();
    }
}