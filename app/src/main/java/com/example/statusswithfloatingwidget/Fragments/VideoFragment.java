package com.example.statusswithfloatingwidget.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusswithfloatingwidget.Adapters.ImageAdapter;
import com.example.statusswithfloatingwidget.Adapters.VideoAdapter;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class VideoFragment extends Fragment {

    RecyclerView recyclerViewVideo;
    String statusDir = "WhatsApp/Media/.Statuses";
    ArrayList<StatusModel> videoArrayList;
    File statusPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View videFragmentView = inflater.from(getActivity()).inflate(R.layout.video_fragment, container, false);
        //View imageFragmentView = inflater.inflate(R.layout.image_fragment, container, false);
        return videFragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewVideo = view.findViewById(R.id.recyclerViewVideo);

        videoArrayList = new ArrayList<>();

        recyclerViewVideo.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        statusPath = new File(Environment.getExternalStorageDirectory()+File.separator+statusDir);

        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchFiles(statusPath);
            }
        }).start();

        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), videoArrayList, VideoFragment.this);
        recyclerViewVideo.setAdapter(videoAdapter);
        
    }

    private void fetchFiles(File statusPath) {

        if(statusPath.exists()){

            if(statusPath.listFiles() != null && statusPath.listFiles().length > 0){

                for(File file : statusPath.listFiles()){

                    if(file.getName().endsWith(".mp4") || file.getName().endsWith(".avi") || file.getName().endsWith(".3gp")){

                        ImageFragment imageFragment = new ImageFragment();
                        StatusModel videoModel = new StatusModel(file.getPath(), imageFragment.getFilesize(file), file);
                        videoModel.setVideoDuration(getVideoDuration(file));
                        videoArrayList.add(videoModel);

                    }

                }

            }

        }


    }

    private String getVideoDuration(File file) {

        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(file.getPath()));
        int duration = mediaPlayer.getDuration();
        return (TimeUnit.MILLISECONDS.toSeconds(duration)+"s");

    }

}
