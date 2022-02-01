package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder> {

    List<KampanyaModel> list;
    Context mContex;
    Activity activity;

    public KampanyaAdapter(List<KampanyaModel> list, Context mContex, Activity activity) {
        this.list = list;
        this.mContex = mContex;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kampanya_baslik, kampanya_text;
        ImageView kampanya_image;
        CardView kampanyaCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kampanya_baslik = itemView.findViewById(R.id.kampanya_baslik);
            kampanya_text = itemView.findViewById(R.id.kampanya_text);
            kampanya_image = itemView.findViewById(R.id.kampanya_image);
            kampanyaCardView = itemView.findViewById(R.id.kampanyaCardView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.kampanya_rv_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.kampanya_baslik.setText(list.get(position).getBaslik()); // kamapanya_baslik text'ine listeden gelen baslik ismini atar.
        holder.kampanya_text.setText(list.get(position).getText()); // kamapanya_text text'ine listeden gelen Text'i atar.

        Picasso.get().load(list.get(position).getResim()).into(holder.kampanya_image); // Picasso kütüphanesi ile listeden gelen resmi imageview'da gösteriririz.

        holder.kampanyaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKampanyaSilAlert(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Silme işlemi için alert dialog açma metodu
    public void openKampanyaSilAlert(int position)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.kampanya_sil_alert_layout,null);

        Button kampanyaSilEvet = view.findViewById(R.id.kampanyaSilEvet);
        Button kampanyaSilIptal = view.findViewById(R.id.kampanyaSilIptal);



        AlertDialog.Builder alert = new AlertDialog.Builder(mContex);
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        kampanyaSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kampanyaSil(list.get(position).getId(),position);
                alertDialog.cancel();
            }
        });

        kampanyaSilIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    public void kampanyaSil(String id, int position)
    {
        Call<KampanyaSilModel> servis = ManagerAll.getOurInstance().kampanyaSil(id);
        servis.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {
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
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {
                Toast.makeText(mContex, Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    // RecyclerView'a güncelleme
    public void deleteToServis(int position) //RecyclerViewi yenileme
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }



}
