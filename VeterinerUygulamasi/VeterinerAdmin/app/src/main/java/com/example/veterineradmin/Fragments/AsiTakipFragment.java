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

import com.example.veterineradmin.Adapters.PetAsiTakipAdapter;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiTakipFragment extends Fragment {

    private View view;
    private DateFormat format;
    private Date date;
    private String today;
    private ChangeFragment changeFragment;

    private RecyclerView asiTakip_rv;
    private List<PetAsiTakipModel> petAsiTakipList;
    private PetAsiTakipAdapter petAsiTakipAdapter;

    private ImageView asiTakipBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_takip, container, false);

        tanimla();
        action();
        istekAt(today);

        return view;
    }

    public void tanimla()
    {
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        today = format.format(date);
        Log.i("bugunnuntarihi",today);

        asiTakip_rv = view.findViewById(R.id.asiTakip_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        asiTakip_rv.setHasFixedSize(true);
        asiTakip_rv.setLayoutManager(manager);

        changeFragment = new ChangeFragment(getContext());

        petAsiTakipList = new ArrayList<>();

        asiTakipBack = view.findViewById(R.id.asiTakipBack);
    }

    public void action()
    {
        asiTakipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new HomeFragment());
            }
        });
    }

    public void istekAt(String tarih)
    {
        Call<List<PetAsiTakipModel>> sevis = ManagerAll.getOurInstance().getAsiPet(tarih);
        sevis.enqueue(new Callback<List<PetAsiTakipModel>>() {
            @Override
            public void onResponse(Call<List<PetAsiTakipModel>> call, Response<List<PetAsiTakipModel>> response) {
                if (response.body().get(0).isTf())
                {
                    petAsiTakipList = response.body();
                    petAsiTakipAdapter = new PetAsiTakipAdapter(petAsiTakipList,getContext(),getActivity());
                    asiTakip_rv.setAdapter(petAsiTakipAdapter);

                    Toast.makeText(getContext(),"Bugün "+response.body().size()+" pete aşı yapılacaktır."
                            ,Toast.LENGTH_LONG).show();
                    //Log.i("bugununpetleriii",response.body().toString());
                }
                else {
                    Toast.makeText(getContext(),"Bugün aşı yapılacak pet yoktur. "
                            ,Toast.LENGTH_LONG).show();
                    changeFragment.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetAsiTakipModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText
                        ,Toast.LENGTH_LONG).show();
            }
        });
    }

}