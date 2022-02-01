package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.veterineradmin.R;
import com.example.veterineradmin.Utils.ChangeFragment;

public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout kampanyaLayout, asiTakipLayout, soruLayout, kullanicilarLayout;
    private ChangeFragment changeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        tanimla();
        clickToLayout();

        return view;
    }

    public void tanimla()
    {
        changeFragment = new ChangeFragment(getContext());

        kampanyaLayout = view.findViewById(R.id.kampanyaLayout);
        asiTakipLayout = view.findViewById(R.id.asiTakipLayout);
        soruLayout = view.findViewById(R.id.soruLayout);
        kullanicilarLayout = view.findViewById(R.id.kullanicilarLayout);
    }

    public void clickToLayout()
    {
        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new KampanyaFragment());
            }
        });

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new AsiTakipFragment());
            }
        });

        soruLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new SorularFragment());
            }
        });

        kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new KullanicilarFragment());
            }
        });
    }
}