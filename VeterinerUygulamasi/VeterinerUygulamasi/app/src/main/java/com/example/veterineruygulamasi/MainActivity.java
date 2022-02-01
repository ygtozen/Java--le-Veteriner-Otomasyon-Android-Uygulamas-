package com.example.veterineruygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.veterineruygulamasi.Activities.GirisYapActivity;
import com.example.veterineruygulamasi.Fragments.HomeFragment;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private GetSharedPreferences preferences;
    private ChangeFragment changeFragment;
    private ImageView anasayfa, aramaYap, cikisYap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragment();

        tanimla();
        kontrol();
        action();
    }

    private void getFragment()
    {
        changeFragment = new ChangeFragment(MainActivity.this);
        changeFragment.change(new HomeFragment());
    }

    public void tanimla()
    {
        preferences = new GetSharedPreferences(MainActivity.this);
        sharedPreferences = preferences.getSession();

        anasayfa = findViewById(R.id.anasayfa);
        aramaYap = findViewById(R.id.aramaYap);
        cikisYap = findViewById(R.id.cikisYap);
    }

    public void kontrol()
    {
        // id,username,mail null ise bizi girisyapActivity'ye gönderir
        if (sharedPreferences.getString("id",null) == null
                && sharedPreferences.getString("username",null) == null
                && sharedPreferences.getString("mailadres",null) == null)
        {
            Intent intent = new Intent(MainActivity.this, GirisYapActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void action()
    {
        anasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.change(new HomeFragment());
            }
        });

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSharedPreferences sharedPreferences = new GetSharedPreferences(MainActivity.this);
                sharedPreferences.deleteToSession();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        aramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:00000000000"));
                startActivity(intent);
            }
        });
    }
}