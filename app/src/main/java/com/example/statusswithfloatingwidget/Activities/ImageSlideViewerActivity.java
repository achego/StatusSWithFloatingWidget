package com.example.statusswithfloatingwidget.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.statusswithfloatingwidget.Adapters.ImageAdapter;
import com.example.statusswithfloatingwidget.Adapters.ImageSliderPagerAdapter;
import com.example.statusswithfloatingwidget.Models.StatusModel;
import com.example.statusswithfloatingwidget.R;

import java.util.ArrayList;

public class ImageSlideViewerActivity extends AppCompatActivity {

    ArrayList<String> imagePaths;
    ViewPager viewPager;
    //String imagePath;
    int imagePosition;
    ImageView imageView;
    ScaleGestureDetector scaleGestureDetector;
    Float scaleFactor = 1.0f;
    View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide_viewer);

        viewPager = findViewById(R.id.imageSliderViewPager);
        imageView = findViewById(R.id.ivImageSlider);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        decorView = getWindow().getDecorView();
        //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if(i == 0){
                    decorView.setSystemUiVisibility(hideSystembars());
                }
            }
        });

        Bundle intent = getIntent().getExtras();

        if(intent != null){

            //imagePaths = intent.getStringArrayList("imageArray");
            imagePosition = intent.getInt("imagePosition");

        }

        Toast.makeText(this, String.valueOf(imagePosition), Toast.LENGTH_SHORT).show();
        //imagePaths.add(imagePath);
        //imagePaths.add(imagePath);

        /*ImageSliderPagerAdapter adapter = new ImageSliderPagerAdapter(getSupportFragmentManager(),
                this, imagePaths, imagePosition);*/

        ImageSliderPagerAdapter adapter = new ImageSliderPagerAdapter(this, imagePosition);

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystembars());
        }
    }

    private int hideSystembars() {

        return View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
            imageView.setScaleX(scaleFactor);
            imageView.setScaleY(scaleFactor);
            return true;
        }
    }



}
