package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.UserAdapter;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KullanicilarFragment extends Fragment {

    private View view;
    private ChangeFragment changeFragment;
    private RecyclerView kullanicilar_rv;
    private List<KullanicilarModel> kullaniciList;
    private UserAdapter userAdapter;

    private ImageView kullanicilarBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);

        tanimla();
        action();
        istekAt();

        return view;
    }

    public void tanimla()
    {
        changeFragment = new ChangeFragment(getContext());

        kullanicilar_rv = view.findViewById(R.id.kullanicilar_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        kullanicilar_rv.setHasFixedSize(true);
        kullanicilar_rv.setLayoutManager(manager);

        kullaniciList = new ArrayList<>();

        kullanicilarBack = view.findViewById(R.id.kullanicilarBack);
    }

    public void action()
    {
        kullanicilarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new HomeFragment());
            }
        });
    }

    public void istekAt()
    {
        Call<List<KullanicilarModel>> servis = ManagerAll.getOurInstance().getKullanici();
        servis.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {
                if (response.body().get(0).isTf()) // List olduğu için
                {
                    //Log.i("kullancigetir",response.body().toString());
                    kullaniciList = response.body();
                    userAdapter = new UserAdapter(kullaniciList,getContext(),getActivity());
                    kullanicilar_rv.setAdapter(userAdapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Sisteme kayıtlı kullanıcı yoktur."
                            ,Toast.LENGTH_LONG).show();
                    changeFragment.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText
                        ,Toast.LENGTH_LONG).show();
            }
        });
    }
}