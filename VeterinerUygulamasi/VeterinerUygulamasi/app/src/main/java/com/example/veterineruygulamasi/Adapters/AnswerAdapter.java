package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Fragments.AnswerModel;
import com.example.veterineruygulamasi.Models.DeleteAnswerModel;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    List<AnswerModel> list;
    Context mContex;

    public AnswerAdapter(List<AnswerModel> list, Context mContex) {
        this.list = list;
        this.mContex = mContex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cevap_soru, txt_cevap_cevap;
        MaterialButton btn_cevapSil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cevap_soru = itemView.findViewById(R.id.txt_cevap_soru);
            txt_cevap_cevap = itemView.findViewById(R.id.txt_cevap_cevap);
            btn_cevapSil = itemView.findViewById(R.id.btn_cevapSil);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.cevap_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_cevap_soru.setText("Soru: " + list.get(position).getSoru());
        holder.txt_cevap_cevap.setText("Cevap: " + list.get(position).getCevap());

        holder.btn_cevapSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(list.get(position).getCevapid().toString(),list.get(position).getSoruid().toString(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteToServis(int position) //RecyclerViewi yenileme
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void delete(String cevapid, String soruid,int pos)
    {
        Call<DeleteAnswerModel> servis = ManagerAll.getOurInstance().deleteAnswer(cevapid, soruid);
        servis.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if (response.body().isTf())
                {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                        deleteToServis(pos);
                    }
                }
                else
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(mContex, Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }


}
