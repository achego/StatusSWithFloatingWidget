package com.example.statusswithfloatingwidget.Adapters;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.statusswithfloatingwidget.Fragments.ImageFragment;
import com.example.statusswithfloatingwidget.Fragments.SavedFilesFragment;
import com.example.statusswithfloatingwidget.Fragments.VideoFragment;

import java.util.ArrayList;
import java.util.Objects;

public class PagerAdapter extends FragmentPagerAdapter {

    ImageFragment imageFragment;
    VideoFragment videoFragment;
    SavedFilesFragment savedFilesFragment;
    //ArrayList<Fragment> fragments;

    Fragment[] fragments;
    String[] pageTitles;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        imageFragment = new ImageFragment();
        videoFragment = new VideoFragment();
        savedFilesFragment = new SavedFilesFragment();

        fragments = new Fragment[]{imageFragment, videoFragment, savedFilesFragment};
        pageTitles = new String[] {"Images", "Videos", "Saved Files"};


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragments[position];

    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitles[position];
        //return super.getPageTitle(position);
    }

}
