package com.example.statusswithfloatingwidget.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statusswithfloatingwidget.Activities.VideoPlayerActivity;
import com.example.statusswithfloatingwidget.Fragments.ImageFragment;
import com.example.statusswithfloatingwidget.Fragments.VideoFragment;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.io.IOException;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context context;
    ArrayList<StatusModel> videoModels;
    VideoFragment videoFragment;

    public VideoAdapter(Context context, ArrayList<StatusModel> videoModels, VideoFragment videoFragment) {
        this.context = context;
        this.videoModels = videoModels;
        this.videoFragment = videoFragment;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
        return new VideoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        final StatusModel videoModel = videoModels.get(position);
        Glide.with(context).load(videoModel.getFilePath()).into(holder.thumbnail);
        holder.fileSize.setText(videoModel.getFileSize());
        holder.videoDuration.setText(videoModel.getVideoDuration());

        /*holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageFragment imageFragment = new ImageFragment();
                try {
                    imageFragment.downloadImage(videoModel.getFilePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoPath", videoModel.getFilePath());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView downloadButton, thumbnail;
        RelativeLayout relativeLayout;
        TextView fileSize, videoDuration;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            downloadButton = itemView.findViewById(R.id.downloadButton);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            fileSize = itemView.findViewById(R.id.fileSize);
            videoDuration = itemView.findViewById(R.id.videoDuration);

        }
    }
}
