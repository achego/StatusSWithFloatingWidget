package com.example.statusswithfloatingwidget.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.statusswithfloatingwidget.Adapters.PagerAdapter;
import com.example.statusswithfloatingwidget.R;
import com.example.statusswithfloatingwidget.Services.BackgroundDownloadService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    Switch backgroundMode;
    TabLayout tabLayout;
    RelativeLayout relativeLayout;
    AppBarLayout appBarLayout;
    SwitchCompat nightMode;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        viewPager = findViewById(R.id.viewpager);
        backgroundMode = findViewById(R.id.startBackgroundservice);
        tabLayout = findViewById(R.id.tabLayout);
        relativeLayout = findViewById(R.id.relativeLayoutBackground);
        appBarLayout = findViewById(R.id.appBarLayout);
        nightMode = findViewById(R.id.toggleNightMode);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            nightMode.setChecked(true);
        }

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartActivity();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartActivity();
                }
            }
        });

        backgroundMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Intent serviceIntent;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                serviceIntent = new Intent(getApplicationContext(), BackgroundDownloadService.class);
                if(b){
                    startService(serviceIntent);
                    //restartActivity();
                }
                else if (!backgroundMode.isChecked()){
                    stopService(serviceIntent);
                    //restartActivity();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void restartActivity() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.history:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.removeAds:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.settings:{
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.nightMode:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.backgroundMode:{
                /*Intent serviceIntent = new Intent(this, BackgroundDownloadService.class);
                startService(serviceIntent);
                finish();*/
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.howTo:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.share:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.rate:{
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.contact:{
                Intent contact = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+2347036941259"));
                startActivity(contact);

            }
            break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
