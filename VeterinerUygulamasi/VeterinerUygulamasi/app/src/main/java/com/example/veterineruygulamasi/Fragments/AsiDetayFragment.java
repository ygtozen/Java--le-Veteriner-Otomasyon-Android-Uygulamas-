package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.SanalKarneGecmisAsiAdapter;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiDetayFragment extends Fragment {

    private View view;
    private String musteri_id;
    private String petid;
    private GetSharedPreferences getSharedPreferences;
    private RecyclerView asiDetay_rv;
    private List<AsiModel> asiList;
    private SanalKarneGecmisAsiAdapter sanalKarneGecmisAsiAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_detay, container, false);

        tanimla();
        getGecmisAsi();

        return view;
    }

    public void tanimla()
    {
        // Gönderilen pet id'sini aldık
        petid = getArguments().getString("petid").toString();
        // Müşteri id'sini alma
        getSharedPreferences = new GetSharedPreferences(getActivity());
        musteri_id = getSharedPreferences.getSession().getString("id",null);

        asiDetay_rv = view.findViewById(R.id.asiDetay_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        asiDetay_rv.setLayoutManager(manager);

        asiList = new ArrayList<>();
    }

    public void getGecmisAsi()
    {
        Call<List<AsiModel>> servis = ManagerAll.getOurInstance().getGecmisAsi(musteri_id,petid);
        servis.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                //Log.i("gecmisasilarrr",response.body().toString());
                if (response.body().get(0).isTf())
                {
                    asiList = response.body();
                    sanalKarneGecmisAsiAdapter = new SanalKarneGecmisAsiAdapter(asiList,getContext());
                    asiDetay_rv.setAdapter(sanalKarneGecmisAsiAdapter);

                    Toast.makeText(getContext(),"Petinize ait "+asiList.size()+" adet geçmişte yapılan aşı bulunkaktadır."
                            ,Toast.LENGTH_LONG).show();
                }
                else
                {
                    ChangeFragment changeFragment = new ChangeFragment(getContext());
                    changeFragment.change(new SanalKarnePetlerFragment());

                    Toast.makeText(getContext(),"Petinize ait geçmiş aşı bulunmamaktadır."
                            ,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }
}