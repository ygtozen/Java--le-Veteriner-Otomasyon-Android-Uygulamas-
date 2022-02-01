package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    List<PetModel> list;
    Context mContex;

    public PetsAdapter(List<PetModel> list, Context mContex) {
        this.list = list;
        this.mContex = mContex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView petname, petcinsname, petturname;
        CircleImageView petImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petname = itemView.findViewById(R.id.petname);
            petcinsname = itemView.findViewById(R.id.petcinsname);
            petturname = itemView.findViewById(R.id.petturneme);
            petImage = itemView.findViewById(R.id.petImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.pet_list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.petname.setText("Pet İsim: " + list.get(position).getPetisim());
        holder.petcinsname.setText("Pet Cinsi: " + list.get(position).getPetcins().toString());
        holder.petturname.setText("Pet Türü: " + list.get(position).getPettur());

        Picasso.get().load(list.get(position).getPetresim()).into(holder.petImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
