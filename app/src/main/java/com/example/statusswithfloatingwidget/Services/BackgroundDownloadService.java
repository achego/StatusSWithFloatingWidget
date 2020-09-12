package com.example.statusswithfloatingwidget.Services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.statusswithfloatingwidget.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class BackgroundDownloadService extends Service {

    WindowManager windowManager;
    View floatingSaveButton;
    WindowManager.LayoutParams params;
    FloatingActionsMenu floatButton;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        floatingSaveButton = LayoutInflater.from(this).inflate(R.layout.background_layout, null);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        floatButton = floatingSaveButton.findViewById(R.id.floatingDownloadButton);

        //Toast.makeText(this, String.valueOf(windowManager!=null), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, String.valueOf(floatingSaveButton.isShown()), Toast.LENGTH_SHORT).show();

        if (windowManager!=null && floatingSaveButton.isShown()){

            windowManager.removeView(floatingSaveButton);
            windowManager = null;

        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        }
        else{

            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        }

        params.x = 30;
        params.y = 30;
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        windowManager.addView(floatingSaveButton, params);
        moveFloatingWidget(params);



        return super.onStartCommand(intent, flags, startId);
    }

    private void moveFloatingWidget(final WindowManager.LayoutParams params) {

        floatButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int initialX = 0 , initialY = 0;
                float initialTouchX = 0, initialTouchY = 0;

                WindowManager.LayoutParams updatedParams = null;

                switch (motionEvent.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        initialY = params.x;
                        initialX = params.y;
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParams.x = (int)(initialX + (motionEvent.getRawX() - initialTouchX));
                        updatedParams.y = (int)(initialY + (motionEvent.getRawY() - initialTouchY));

                        windowManager.updateViewLayout(floatingSaveButton, updatedParams);
                        break;

                    default:
                        break;

                }

                return false;

            }
        });

    }
}
