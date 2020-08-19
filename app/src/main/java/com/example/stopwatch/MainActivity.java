package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {
int height,width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Fragment fragment;
        RelativeLayout relativeLayout =findViewById(R.id.act);
        if(this.getResources().getConfiguration().orientation== ORIENTATION_LANDSCAPE)
        { height =getResources().getDisplayMetrics().heightPixels;
            width =getResources().getDisplayMetrics().heightPixels;
            relativeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            relativeLayout.setMinimumHeight(height);}
        else {height =getResources().getDisplayMetrics().widthPixels;
            width =getResources().getDisplayMetrics().widthPixels;
            relativeLayout.setGravity(Gravity.CENTER);}
        fragment = CanvasFragment.newInstance(width,height);
        Fragment fragment1=new ButtonFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.but,fragment1).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.can,fragment).commit();
    }


}
