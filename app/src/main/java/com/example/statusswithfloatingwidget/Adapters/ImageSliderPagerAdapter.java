package com.example.statusswithfloatingwidget.Adapters;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.io.File;
import java.util.ArrayList;

public class ImageSliderPagerAdapter extends PagerAdapter {

    Context context;
    int pos;
    boolean check = false;

    File statusPath = new File(Environment.getExternalStorageDirectory()+File.separator+"WhatsApp/Media/.Statuses");
    ArrayList<File> fileArray;


    public ImageSliderPagerAdapter(Context context, int pos) {
        this.context = context;
        this.pos = pos;

        fileArray = new ArrayList<>();

        for(File file : statusPath.listFiles()) {

            if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                fileArray.add(file);
            }
        }
    }

    @Override
    public int getCount() {
        return fileArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
        if(check == false){
            position = position + pos;
            check = true;
        }


        View view = LayoutInflater.from(context).inflate(R.layout.custom_slide_view, container, false);

        ImageView image = view.findViewById(R.id.ivImageSlider);
        Glide.with(context).load(fileArray.get(position)).into(image);

        //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
