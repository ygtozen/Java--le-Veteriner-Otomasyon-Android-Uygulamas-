package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Fragments.AsiDetayFragment;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAsiAdapter extends RecyclerView.Adapter<SanalKarneGecmisAsiAdapter.ViewHolder> {

    List<AsiModel> list;
    Context mContex;

    public SanalKarneGecmisAsiAdapter(List<AsiModel> list, Context mContex) {
        this.list = list;
        this.mContex = mContex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_sanalkarnegecmisasiismi, txt_sanalkarnegemisasibilgi;
        CircleImageView sanalkarnegemisasi_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sanalkarnegecmisasiismi = itemView.findViewById(R.id.txt_sanalkarnegecmisasiismi);
            txt_sanalkarnegemisasibilgi = itemView.findViewById(R.id.txt_sanalkarnegemisasibilgi);
            sanalkarnegemisasi_image = itemView.findViewById(R.id.sanalkarnegemisasi_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.sanal_karne_gecmisasi_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_sanalkarnegecmisasiismi.setText(list.get(position).getAsiisim() + " aşısı yapılmıştır.");
        holder.txt_sanalkarnegemisasibilgi.setText(list.get(position).getPetisim() + " isimli petinize "
        + list.get(position).getAsitarih() + " tarihinde " + list.get(position).getAsiisim()
        + " aşısı yapılmıştır.");

        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalkarnegemisasi_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
