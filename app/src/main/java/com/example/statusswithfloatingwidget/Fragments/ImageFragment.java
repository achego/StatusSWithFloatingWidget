package com.example.statusswithfloatingwidget.Fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusswithfloatingwidget.Adapters.ImageAdapter;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImageFragment extends Fragment {

    private RecyclerView recyclerViewImage;
    private String statusDir = "WhatsApp/Media/.Statuses";
    private String appDir = "StatusSaved";
    private ArrayList<StatusModel> imageArrayList;
    private File statusPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View imageFragmentView = inflater.inflate(R.layout.image_fragment, container, false);
        //View imageFragmentView = inflater.inflate(R.layout.image_fragment, container, false);
        return imageFragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewImage = view.findViewById(R.id.recyclerViewImage);

        imageArrayList = new ArrayList<>();

        recyclerViewImage.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        statusPath = new File(Environment.getExternalStorageDirectory()+File.separator+statusDir);

        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchFiles(statusPath);
            }
        }).start();

        //fetchFiles(statusPath);

        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), imageArrayList, this);
        recyclerViewImage.setAdapter(imageAdapter);

    }

    private void fetchFiles(File statusPath) {

        if(statusPath.exists()){

            if(statusPath.listFiles() != null && statusPath.listFiles().length > 0){

                for(File file : statusPath.listFiles()){

                    if(file.getName().endsWith(".jpg") || file.getName().endsWith(".png")){

                        StatusModel imageModel = new StatusModel(file.getPath(), getFilesize(file), file);
                        imageArrayList.add(imageModel);

                    }

                }

            }
        }
    }

    public String getFilesize(File file) {
        long byteSize = file.length();
        String size = "";
        if(byteSize > 999999){

            int mb = (int)(byteSize/1000000.0);
            int kb = (int)(((byteSize/1000000.0) - mb) * 100);
            //int kb = Math.round(((byteSize/10000.0) - mb) * 100));
            size = mb+"."+kb+" MB";

        }
        else{

            int kb = (int)(byteSize/1000.0);
            int by = (int)(((byteSize/1000.0) - kb) * 100);
            size = kb+"."+by+" KB";

        }

        return size;

    }


    public void downloadImage(StatusModel statusModel) throws IOException {

        File appDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+appDir);
        Toast.makeText(getActivity(), statusModel.getFilePath(), Toast.LENGTH_LONG).show();
        File destinationFile = new File(appDirectory + File.separator + statusModel.getFile().getName());

        if(!appDirectory.exists()){

            appDirectory.mkdir();
            Toast.makeText(getActivity(), "dir created", Toast.LENGTH_LONG).show();

        }

        if(destinationFile.exists()){

            Toast.makeText(getActivity(), "File exists already", Toast.LENGTH_SHORT).show();
            destinationFile.delete();

        }else{
            Toast.makeText(getActivity(), "File does not exist", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getActivity(), "from : "+new File(statusModel.getFilePath()).toString()+" to : "+destinationFile.toString(), Toast.LENGTH_LONG).show();


        copyFileToAppDir(new File(statusModel.getFilePath()), destinationFile);

        //Files.copy(fileToBeDownloaded.toPath(), appDirectory.toPath());



        Toast.makeText(getActivity(), "download completed", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destinationFile));
        getActivity().sendBroadcast(intent);

    }

    private void copyFileToAppDir(File fileToBeDownloaded, File appDirectory) throws IOException {

        Toast.makeText(getActivity(), "coping File", Toast.LENGTH_SHORT).show();


        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(fileToBeDownloaded).getChannel();
        destination = new FileOutputStream(appDirectory).getChannel();

        destination.transferFrom(source, 0, source.size());

        source.close();
        destination.close();


    }

}
