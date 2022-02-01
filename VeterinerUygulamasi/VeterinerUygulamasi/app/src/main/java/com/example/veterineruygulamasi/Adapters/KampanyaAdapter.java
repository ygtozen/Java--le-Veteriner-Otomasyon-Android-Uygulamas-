package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Fragments.AnswerModel;
import com.example.veterineruygulamasi.Models.DeleteAnswerModel;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder> {

    List<KampanyaModel> list;
    Context mContex;

    public KampanyaAdapter(List<KampanyaModel> list, Context mContex) {
        this.list = list;
        this.mContex = mContex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kampanya_baslik, kampanya_text;
        ImageView kampanya_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kampanya_baslik = itemView.findViewById(R.id.kampanya_baslik);
            kampanya_text = itemView.findViewById(R.id.kampanya_text);
            kampanya_image = itemView.findViewById(R.id.kampanya_image);
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

        holder.kampanya_baslik.setText(list.get(position).getBaslik());
        holder.kampanya_text.setText(list.get(position).getText());

        Picasso.get().load(list.get(position).getResim()).into(holder.kampanya_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
