package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.Models.SoruModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.ViewHolder> {

    List<SoruModel> list;
    Context mContex;
    Activity activity;

    public VeterinerSoruAdapter(List<SoruModel> list, Context mContex, Activity activity) {
        this.list = list;
        this.mContex = mContex;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soru_kadi, soru_soruText;
        ImageView soru_cevapla , soru_arama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            soru_kadi = itemView.findViewById(R.id.soru_kadi);
            soru_soruText = itemView.findViewById(R.id.soru_soruText);
            soru_cevapla = itemView.findViewById(R.id.soru_cevapla);
            soru_arama = itemView.findViewById(R.id.soru_arama);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.sorular_rv_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.soru_kadi.setText(list.get(position).getKadi());
        holder.soru_soruText.setText(list.get(position).getSoru());

        holder.soru_arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ara(list.get(position).getTelefon());
            }
        });

        holder.soru_cevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevaplaAlert(list.get(position).getMusteriid(), list.get(position).getSoruid(),
                        position,list.get(position).getSoru());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    // RecyclerView'a güncelleme
    public void deleteToServis(int position) //RecyclerViewi yenileme
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void ara(String numara)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }


    // Alert dialogu açma
    public void cevaplaAlert(String musteri_id,String soru_id,int position,String soru)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.cevapla_alertdialog_layout,null);

        EditText cevapEditText = view.findViewById(R.id.cevapEditText);
        MaterialButton btn_cevapla = view.findViewById(R.id.btn_cevapla);
        TextView cevaplanacakSoruText = view.findViewById(R.id.cevaplanacakSoruText);

        cevaplanacakSoruText.setText(soru);

        AlertDialog.Builder alert = new AlertDialog.Builder(mContex);
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        btn_cevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cevap = cevapEditText.getText().toString();
                if (!cevap.equals(""))
                {
                    //cevapEditText.setText("");
                    alertDialog.cancel();
                    cevapla(musteri_id,soru_id,cevap,alertDialog,position);
                }
            }
        });

        alertDialog.show();
    }

    public void cevapla(String musterid, String soruid, String text, AlertDialog alertDialog,int position)
    {
        Call<CevaplaModel> servis = ManagerAll.getOurInstance().cevapla(musterid, soruid, text);
        servis.enqueue(new Callback<CevaplaModel>() {
            @Override
            public void onResponse(Call<CevaplaModel> call, Response<CevaplaModel> response) {
                if (response.body().isTf())
                {
                    deleteToServis(position);
                    alertDialog.cancel();
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<CevaplaModel> call, Throwable t) {
                Toast.makeText(mContex,Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }




}
