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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.PetAdapter;
import com.example.veterineradmin.Models.PetEkleModel;
import com.example.veterineradmin.Models.PetModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KullaniciPetlerFragment extends Fragment {

    private View view;
    private String musid;
    private Bundle bundle;
    private ChangeFragment changeFragment;
    private RecyclerView userPet_rv;
    private ImageView petEkleResimYok;
    private TextView petEkleUyariText;
    private Button userPetEkle;
    private List<PetModel> petList;
    private PetAdapter petAdapter;
    private ImageView petEkleImage;
    private Bitmap bitmap;
    private String imageString="";

    private ImageView kullaniciPetlerBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanici_petler, container, false);

        tanimla();
        getPetsIstekAt(musid);
        action();

        return view;
    }

    public void tanimla()
    {
        // gönderilen id'yi aldık
        musid = getArguments().get("userid").toString();
        Log.i("gelenmusid",musid);

        changeFragment = new ChangeFragment(getContext());

        userPet_rv = view.findViewById(R.id.userPet_rv);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1);
        userPet_rv.setHasFixedSize(true);
        userPet_rv.setLayoutManager(manager);

        petEkleResimYok = view.findViewById(R.id.petEkleResimYok);
        petEkleUyariText = view.findViewById(R.id.petEkleUyariText);
        userPetEkle = view.findViewById(R.id.userPetEkle);

        petList = new ArrayList<>();

        bitmap = null;

        kullaniciPetlerBack = view.findViewById(R.id.kullaniciPetlerBack);
    }

    public void action()
    {
        userPetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petEkleAlert();
            }
        });

        kullaniciPetlerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.change(new KullanicilarFragment());
            }
        });
    }

    public void getPetsIstekAt(String id)
    {
        Call<List<PetModel>> servis = ManagerAll.getOurInstance().getPets(id);
        servis.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if (response.body().get(0).isTf())
                {
                    userPet_rv.setVisibility(View.VISIBLE);
                    petEkleResimYok.setVisibility(View.GONE);
                    petEkleUyariText.setVisibility(View.GONE);
                    petList = response.body();
                    petAdapter = new PetAdapter(petList,getContext(),getActivity(),musid);
                    userPet_rv.setAdapter(petAdapter);

                    Toast.makeText(getContext(),"Kullancıya ait "+response.body().size()
                    +" adet pet vardır.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Kullanıcıya ait pet bulunamamıştır"
                            ,Toast.LENGTH_SHORT).show();
                    petEkleResimYok.setVisibility(View.VISIBLE);
                    petEkleUyariText.setVisibility(View.VISIBLE);
                    userPet_rv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText
                        ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Alert dialogu açma
    public void petEkleAlert()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pet_ekle_alert_layout,null);

        EditText petEkleNameText = view.findViewById(R.id.petEkleNameText);
        EditText petEkleTurText = view.findViewById(R.id.petEkleTurText);
        EditText petEkleCinsText = view.findViewById(R.id.petEkleCinsText);
        petEkleImage = view.findViewById(R.id.petEkleImage);
        Button btn_petekle = view.findViewById(R.id.btn_petekle);
        Button btn_petekle_resimsec = view.findViewById(R.id.btn_petekle_resimsec);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        btn_petekle_resimsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galeriAc();
            }
        });

        btn_petekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imageToString().equals("") &&
                        !petEkleNameText.getText().toString().equals("") &&
                        !petEkleTurText.getText().toString().equals("") &&
                        !petEkleCinsText.getText().toString().equals(""))
                {
                    // Pet Ekleme Metodunu çağırdık
                    petEkle(musid,petEkleNameText.getText().toString(),
                            petEkleTurText.getText().toString(),petEkleCinsText.getText().toString()
                            ,imageToString(),alertDialog);


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
                petEkleImage.setImageBitmap(bitmap);
                petEkleImage.setVisibility(View.VISIBLE);
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
            imageString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imageString;
        }
        else
        {
            return imageString;
        }
    }

    public void petEkle(String musid,String isim,String tur,String cins,String resim, AlertDialog alertDialog)
    {
        Call<PetEkleModel> servis = ManagerAll.getOurInstance().petEkle(musid, isim, tur, cins, resim);
        servis.enqueue(new Callback<PetEkleModel>() {
            @Override
            public void onResponse(Call<PetEkleModel> call, Response<PetEkleModel> response) {
                if (response.body().isTf())
                {
                    getPetsIstekAt(musid);
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
                else
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<PetEkleModel> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_SHORT).show();
            }
        });
    }

}