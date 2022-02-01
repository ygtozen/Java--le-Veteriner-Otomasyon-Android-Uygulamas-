package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Fragments.AsiTakipFragment;
import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetAsiTakipAdapter extends RecyclerView.Adapter<PetAsiTakipAdapter.ViewHolder> {

    List<PetAsiTakipModel> list;
    Context mContex;
    Activity activity;

    public PetAsiTakipAdapter(List<PetAsiTakipModel> list, Context mContex, Activity activity) {
        this.list = list;
        this.mContex = mContex;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView asiTakipPetName, asiTakipBilgiText;
        ImageView asiTakip_image , asiTakipOk, asiTakipCancel, asiTakipAra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            asiTakipPetName = itemView.findViewById(R.id.asiTakipPetName);
            asiTakipBilgiText = itemView.findViewById(R.id.asiTakipBilgiText);
            asiTakip_image = itemView.findViewById(R.id.asiTakip_image);
            asiTakipOk = itemView.findViewById(R.id.asiTakipOk);
            asiTakipCancel = itemView.findViewById(R.id.asiTakipCancel);
            asiTakipAra = itemView.findViewById(R.id.asiTakipAra);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.asi_takip_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.asiTakipPetName.setText(list.get(position).getPetisim());
        holder.asiTakipBilgiText.setText(list.get(position).getKadi() + " isimli kullanıcının "
        + list.get(position).getPetisim() + " isimli petinin " + list.get(position).getAsiisim()
        + " aşısı yapılacaktır.");

        Picasso.get().load(list.get(position).getPetresim()).into(holder.asiTakip_image);

        holder.asiTakipAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ara(list.get(position).getTelefon());
            }
        });

        holder.asiTakipOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asiOnayla(list.get(position).getAsiid(),position);
            }
        });

        holder.asiTakipCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asiIptal(list.get(position).getAsiid(),position);
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

    public void asiOnayla(String id,int position)
    {
        Call<AsiOnaylaModel> servis = ManagerAll.getOurInstance().asiOnayla(id);
        servis.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                    deleteToServis(position);
                }
                else
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(mContex,Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void asiIptal(String id, int position)
    {
        Call<AsiOnaylaModel> servis = ManagerAll.getOurInstance().asiIptal(id);
        servis.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                deleteToServis(position);
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(mContex,Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }


}
