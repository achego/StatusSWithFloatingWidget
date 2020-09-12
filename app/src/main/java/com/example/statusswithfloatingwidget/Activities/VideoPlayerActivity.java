package com.example.statusswithfloatingwidget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.statusswithfloatingwidget.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {

    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    Uri videoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //ImageSlideViewerActivity imageSlide = new ImageSlideViewerActivity();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        playerView = findViewById(R.id.playerView);

        Bundle intent = getIntent().getExtras();

        if(intent != null){

            String path = intent.getString("videoPath");
            videoUri = Uri.parse(path);

        }

        TrackSelector trackSelector = new DefaultTrackSelector();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        String userAgent = Util.getUserAgent(this, "Player");
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);

        MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory,
                null, null);

        playerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
        exoPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
    }
}
