package com.example.statusswithfloatingwidget.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statusswithfloatingwidget.Activities.ImageSlideViewerActivity;
import com.example.statusswithfloatingwidget.Fragments.ImageFragment;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    Context context;
    ArrayList<StatusModel> imageModels;
    ImageFragment imageFragment;

    public ImageAdapter(Context context, ArrayList<StatusModel> imageModels, ImageFragment imageFragment) {
        this.context = context;
        this.imageModels = imageModels;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listView = LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
        return new ImageViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {

        final StatusModel imageModel = imageModels.get(position);
        holder.playButton.setVisibility(View.GONE);
        holder.duration.setVisibility(View.GONE);
        Glide.with(context).load(imageModel.getFilePath()).into(holder.thumbnail);
        holder.fileSize.setText(imageModel.getFileSize());

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                try {
                    //Toast.makeText(context, "This is Adapter", Toast.LENGTH_LONG).show();

                    imageFragment.downloadImage(imageModel);
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageSlideViewerActivity.class);
                //intent.putExtra("imageArrays", imageModels);
                intent.putExtra("imagePosition", position);
                context.startActivity(intent);
               // intent.putExtra("imagePathArray", imageModels);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView playButton, downloadButton, thumbnail;
        RelativeLayout relativeLayout;
        TextView fileSize, duration;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            playButton = itemView.findViewById(R.id.playButton);
            downloadButton = itemView.findViewById(R.id.downloadButton);
            duration = itemView.findViewById(R.id.videoDuration);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            fileSize = itemView.findViewById(R.id.fileSize);


        }
    }
}
