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

import com.example.veterineradmin.Fragments.KullaniciPetlerFragment;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<KullanicilarModel> list;
    Context mContex;
    Activity activity;
    ChangeFragment changeFragment;

    public UserAdapter(List<KullanicilarModel> list, Context mContex, Activity activity) {
        this.list = list;
        this.mContex = mContex;
        this.activity = activity;
        changeFragment = new ChangeFragment(mContex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kullaniciNameText;
        Button userPetler, userAra;
        CardView userCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kullaniciNameText = itemView.findViewById(R.id.kullaniciNameText);
            userCardView = itemView.findViewById(R.id.userCardView);
            userPetler = itemView.findViewById(R.id.userPetler);
            userAra = itemView.findViewById(R.id.userAra);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.kullanicilar_rv_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.kullaniciNameText.setText(list.get(position).getKadi()); // kullaniciNameText text'ine listeden gelen k.adi ismini atar.

        holder.userAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ara(list.get(position).getTelefon());
            }
        });

        holder.userPetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment.changeWithParametre(new KullaniciPetlerFragment(),list.get(position).getId());
            }
        });

        holder.userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKullaniciSilAlert(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void ara(String numara)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }

    // Silme işlemi için alert dialog açma metodu
    public void openKullaniciSilAlert(int position)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.kullanici_sil_alert_layout,null);

        Button kullaniciSilEvet = view.findViewById(R.id.kullaniciSilEvet);
        Button kullaniciSilIptal = view.findViewById(R.id.kullaniciSilIptal);



        AlertDialog.Builder alert = new AlertDialog.Builder(mContex);
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        kullaniciSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullaniciSl(list.get(position).getId(),position);
                alertDialog.cancel();
            }
        });

        kullaniciSilIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    public void kullaniciSl(String id,int position)
    {
        Call<KullaniciSilModel> servis = ManagerAll.getOurInstance().kullanciSil(id);
        servis.enqueue(new Callback<KullaniciSilModel>() {
            @Override
            public void onResponse(Call<KullaniciSilModel> call, Response<KullaniciSilModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_SHORT).show();
                    deleteToServis(position);
                }
                else
                {
                    Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KullaniciSilModel> call, Throwable t) {
                Toast.makeText(mContex,Warnings.internetProblemText,Toast.LENGTH_SHORT).show();
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
