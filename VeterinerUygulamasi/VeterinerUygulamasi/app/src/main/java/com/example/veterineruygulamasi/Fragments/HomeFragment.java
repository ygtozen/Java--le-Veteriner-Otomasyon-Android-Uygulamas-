package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.AnswerAdapter;
import com.example.veterineruygulamasi.Models.AskQuestionModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout petLayout, sorusorLayout, cevapLayout, kampanyaLayout, asiTakipLayout, sanalkarneLayout;
    private ChangeFragment changeFragment;
    private GetSharedPreferences getSharedPreferences;
    private String musteri_id; // Müşteri id sini aldık
    private AnswerAdapter answerAdapter;
    private List<AnswerModel> answerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        tanimla();
        action();

        return view;
    }

    public void tanimla()
    {
        answerList = new ArrayList<>();
        changeFragment = new ChangeFragment(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        musteri_id = getSharedPreferences.getSession().getString("id",null);

        petLayout = view.findViewById(R.id.petLayout);
        sorusorLayout = view.findViewById(R.id.sorusorLayout);
        cevapLayout = view.findViewById(R.id.cevapLayout);
        kampanyaLayout = view.findViewById(R.id.kampanyaLayout);
        asiTakipLayout = view.findViewById(R.id.asiTakipLayout);
        sanalkarneLayout = view.findViewById(R.id.sanalkarneLayout);
    }

    public void action()
    {
        petLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.change(new UserPetsFragment());
            }
        });

        sorusorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionAlert();
            }
        });

        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswers(musteri_id);
            }
        });

        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.change(new KampanyaFragment());
            }
        });

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.change(new AsiFragment());
            }
        });

        sanalkarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.change(new SanalKarnePetlerFragment());
            }
        });

    }

    // Alert dialogu açma
    public void openQuestionAlert()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.sorusor_alertdialog_layout,null);

        EditText soruEditText = view.findViewById(R.id.soruEditText);
        MaterialButton btn_sorusor = view.findViewById(R.id.btn_sorusor);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        btn_sorusor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soru = soruEditText.getText().toString();
                if (!soru.equals(""))
                {
                    soruEditText.setText("");
                    alertDialog.cancel();
                    askQuestion(musteri_id,soru,alertDialog);
                }
            }
        });

        alertDialog.show();
    }

    public void askQuestion(String mus_id, String soru,AlertDialog alertDialog)
    {
        Call<AskQuestionModel> servis = ManagerAll.getOurInstance().soruSor(mus_id, soru);
        servis.enqueue(new Callback<AskQuestionModel>() {
            @Override
            public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
                if (response.body().isTf()) // Soru başarılı bir şekilde veterinere iletildi ise
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel(); // Gönderme işlemi başarılı ise alertdialog kapanır
                }
                else // Soru iletilmedi ise
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AskQuestionModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAnswers(String mus_id)
    {
        Call<List<AnswerModel>> servis = ManagerAll.getOurInstance().getAnswer(mus_id);
        servis.enqueue(new Callback<List<AnswerModel>>() {
            @Override
            public void onResponse(Call<List<AnswerModel>> call, Response<List<AnswerModel>> response) {
                if (response.body().get(0).isTf())
                {
                    //Log.i("gelencevapppp",response.body().toString());
                    if (response.isSuccessful()) {
                        answerList = response.body();
                        answerAdapter = new AnswerAdapter(answerList,getContext());
                        openAnswerAlert();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Herhangi bir cevap yok.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnswerModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    // Cevaplar için alert aialog açma
    public void openAnswerAlert()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.cevap_alert_layout,null);

        RecyclerView cevap_rv = view.findViewById(R.id.cevap_rv);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

       RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
       cevap_rv.setLayoutManager(manager);
       cevap_rv.setAdapter(answerAdapter);


        alertDialog.show();
    }

}