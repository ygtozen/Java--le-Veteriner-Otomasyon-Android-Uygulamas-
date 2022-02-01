package com.example.veterineradmin.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.KampanyaAdapter;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanya_rv;
    private List<KampanyaModel> kampanyaList;
    private KampanyaAdapter kampanyaAdapter;
    private ChangeFragment changeFragment;
    private MaterialButton btn_kampanyaEkle;
    private ImageView kampanyaEkleImage ,kampanyaBack;
    private Bitmap bitmap;
    private String imagaString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);

        tanimla();
        action();
        getKampanya();

        return view;
    }

    public void tanimla()
    {
        changeFragment = new ChangeFragment(getContext());

        kampanya_rv = view.findViewById(R.id.kampanya_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        kampanya_rv.setLayoutManager(manager);

        kampanyaBack = view.findViewById(R.id.kampanyaBack);


        kampanyaList = new ArrayList<>();

        btn_kampanyaEkle = view.findViewById(R.id.btn_kampanyaEkle);

        bitmap = null;
        imagaString = "";
    }

    public void action()
    {
        btn_kampanyaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKampanyaAlert();
            }
        });

        kampanyaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new HomeFragment());
            }
        });
    }

    // Alert dialogu açma
    public void openKampanyaAlert()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.kampanya_ekle_alert_layout,null);

        EditText kampanyaBaslikEditText = view.findViewById(R.id.kampanyaBaslikEditText);
        EditText kampanyaIcerikText = view.findViewById(R.id.kampanyaIcerikText);
        kampanyaEkleImage = view.findViewById(R.id.kampanyaEkleImage);
        Button btn_kamEkle = view.findViewById(R.id.btn_kamEkle);
        Button btn_kam_imageEkle = view.findViewById(R.id.btn_kam_imageEkle);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        btn_kam_imageEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galeriAc();
            }
        });

        btn_kamEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imageToString().equals("") &&
                        !kampanyaBaslikEditText.getText().toString().equals("") &&
                !kampanyaIcerikText.getText().toString().equals(""))
                {
                    // Kampanya Ekleme Metodunu çağırdık
                    kampanyaEkle(kampanyaBaslikEditText.getText().toString()
                            ,kampanyaIcerikText.getText().toString()
                    ,imageToString(), alertDialog);

                    /*kampanyaBaslikEditText.setText("");
                    kampanyaIcerikText.setText("");*/
                }
                else
                {
                    Toast.makeText(getContext(),
                            "Tüm alanların doldurulması ve resmin seçilmesi zorunludur."
                    ,Toast.LENGTH_LONG).show();
                }
            }
        });


        alertDialog.show();
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
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext(),getActivity());
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

    public void galeriAc()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == Activity.RESULT_OK && data != null) // if olmadan da çalışır
        {
            Uri path = data.getData();
            try {// resmin imageview ile gösterilmesi için
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                kampanyaEkleImage.setImageBitmap(bitmap);
                kampanyaEkleImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() // Base64 çevirme
    {
        if (bitmap != null)
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imagaString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imagaString;
        }
        else
        {
            return imagaString;
        }
    }

    public void kampanyaEkle(String baslik, String icerik, String resim, AlertDialog alertDialog)
    {
        Call<KampanyaEkleModel> servis = ManagerAll.getOurInstance().addKampanya(baslik, icerik, resim);
        servis.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(getContext(),response.body().getSonuc(),Toast.LENGTH_LONG).show();
                    // Eğer true dönerse yani kampanya ekleme başarılı ise kampanya çağırma metodunu tekrar çağırıyoruz.
                    getKampanya();
                    alertDialog.cancel();
                }
                else
                {
                    Toast.makeText(getContext(),response.body().getSonuc(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

}