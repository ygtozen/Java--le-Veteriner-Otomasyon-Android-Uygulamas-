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
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.ViewHolder> {

    List<PetModel> list;
    Context mContex;

    public SanalKarnePetAdapter(List<PetModel> list, Context mContex) {
        this.list = list;
        this.mContex = mContex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_sanalkarnepet, txt_sanalkarnebilgi;
        CircleImageView sanalkarnepet_image;
        CardView sanalkarneCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sanalkarnepet = itemView.findViewById(R.id.txt_sanalkarnepet);
            txt_sanalkarnebilgi = itemView.findViewById(R.id.txt_sanalkarnebilgi);
            sanalkarnepet_image = itemView.findViewById(R.id.sanalkarnepet_image);
            sanalkarneCard = itemView.findViewById(R.id.sanalkarneCard);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.sanal_karne_pet_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_sanalkarnepet.setText(list.get(position).getPetisim());
        holder.txt_sanalkarnebilgi.setText(list.get(position).getPetisim()+ " isimli "
                +list.get(position).getPettur()+" türünde "
                +list.get(position).getPetcins()+ " cinsli petinize ait geçmiş aşıları görmek için tıklayınız.");

        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalkarnepet_image);

        holder.sanalkarneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment changeFragment = new ChangeFragment(mContex);
                changeFragment.changeWithParametre(new AsiDetayFragment(),list.get(position).getPetid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
