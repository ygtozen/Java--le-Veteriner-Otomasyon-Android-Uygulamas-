package com.example.veterineradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.veterineradmin.Fragments.HomeFragment;
import com.example.veterineradmin.Utils.ChangeFragment;

public class MainActivity extends AppCompatActivity {

    private ChangeFragment changeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment = new ChangeFragment(MainActivity.this);
        changeFragment.change(new HomeFragment());
    }
}