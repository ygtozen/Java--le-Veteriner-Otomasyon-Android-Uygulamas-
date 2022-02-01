package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.VeterinerSoruAdapter;
import com.example.veterineradmin.Models.SoruModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SorularFragment extends Fragment {

    private View view;
    private RecyclerView soru_rv;
    private List<SoruModel> soruList;
    private VeterinerSoruAdapter veterinerSoruAdapter;
    private ChangeFragment changeFragment;

    private ImageView sorularBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorular, container, false);

        tanimla();
        action();
        istekAt();

        return view;
    }

    public void tanimla()
    {
        changeFragment = new ChangeFragment(getContext());

        soru_rv = view.findViewById(R.id.soru_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        soru_rv.setLayoutManager(manager);
        soru_rv.setHasFixedSize(true);

        sorularBack = view.findViewById(R.id.sorularBack);

        soruList = new ArrayList<>();
    }

    public void action()
    {
        sorularBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new HomeFragment());
            }
        });
    }

    public void istekAt()
    {
        Call<List<SoruModel>> servis = ManagerAll.getOurInstance().getSoru();
        servis.enqueue(new Callback<List<SoruModel>>() {
            @Override
            public void onResponse(Call<List<SoruModel>> call, Response<List<SoruModel>> response) {
                if (response.body().get(0).isTf())
                {
                    soruList = response.body();
                    veterinerSoruAdapter = new VeterinerSoruAdapter(soruList,getContext(),getActivity());
                    soru_rv.setAdapter(veterinerSoruAdapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Veteriner hekime sorulan soru yoktur"
                            ,Toast.LENGTH_LONG).show();
                    changeFragment.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SoruModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText
                        ,Toast.LENGTH_LONG).show();
            }
        });
    }
}