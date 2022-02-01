package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.KampanyaAdapter;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.example.veterineruygulamasi.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanya_rv;
    private ChangeFragment changeFragment;
    private List<KampanyaModel> kampanyaList;
    private KampanyaAdapter kampanyaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);

        tanimla();
        getKampanya();

        return view;
    }

    public void tanimla()
    {
        changeFragment = new ChangeFragment(getContext());
        kampanyaList = new ArrayList<>();

        kampanya_rv = view.findViewById(R.id.kampanya_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        kampanya_rv.setLayoutManager(manager);
    }

    public void getKampanya()
    {
        Call<List<KampanyaModel>> servis = ManagerAll.getOurInstance().getKampanya();
        servis.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {
                if (response.body().get(0).isTf()) // Liste olduğu için 0. elamana bakıyoruz.
                {
                    kampanyaList = response.body();
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext());
                    kampanya_rv.setAdapter(kampanyaAdapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Herhangi bir kampanya bulunmamaktadır.",Toast.LENGTH_LONG).show();
                    changeFragment.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}