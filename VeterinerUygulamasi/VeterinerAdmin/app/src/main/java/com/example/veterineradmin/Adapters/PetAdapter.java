package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Fragments.KullaniciPetlerFragment;
import com.example.veterineradmin.Models.AsiEkleModel;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.Models.PetModel;
import com.example.veterineradmin.Models.PetSilModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragment;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    List<PetModel> list;
    Context mContex;
    Activity activity;
    ChangeFragment changeFragment;
    String musid;
    String tarih = "", formatliTarih="";

    public PetAdapter(List<PetModel> list, Context mContex, Activity activity,String musid) {
        this.list = list;
        this.mContex = mContex;
        this.activity = activity;
        this.musid = musid;
        changeFragment = new ChangeFragment(mContex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView petBilgiText,petNameText;
        CardView petCardView;
        ImageView petImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petBilgiText = itemView.findViewById(R.id.petBilgiText);
            petNameText = itemView.findViewById(R.id.petNameText);
            petImage = itemView.findViewById(R.id.petImage);
            petCardView = itemView.findViewById(R.id.petCardView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.userpet_rv_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.petNameText.setText(list.get(position).getPetisim().toString());
        /*holder.petBilgiText.setText("Bu petin turu "+list.get(position).getPettur().toString()
        +" ,cinsi "+list.get(position).getPetcins().toString()+". "+list.get(position).getPetisim()
        +" isimli pete aşı eklemek için tıklayın.");*/
        holder.petBilgiText.setText(list.get(position).getPetisim() +
                " isimli peti silmek için buraya yıklayın");

        holder.petBilgiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetSilAlert(position);
            }
        });

        //Picasso.get().load(list.get(position).getPetresim().toString()).resize(150,150).into(holder.petImage);
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.petImage);

        holder.petCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asiEkleAlert(list.get(position).getPetid().toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // Alert dialogu açma
    public void asiEkleAlert(String petid)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.asi_ekle_alert_layout,null);

        CalendarView asiEkleTakvim = view.findViewById(R.id.asiEkleTakvim);
        EditText asiEkleName = view.findViewById(R.id.asiEkleName);
        Button btn_asiekle = view.findViewById(R.id.btn_asiekle);


        AlertDialog.Builder alert = new AlertDialog.Builder(mContex);
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        asiEkleTakvim.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                //DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                tarih = i2 + "/" + (i1+1) + "/" + i;

                try {
                    Date date = inputFormat.parse(tarih);

                    formatliTarih = format.format(date).toString();
                    Toast.makeText(mContex,formatliTarih,Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_asiekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!formatliTarih.equals("") && !asiEkleName.getText().toString().equals(""))
                {
                    addAsi(musid,petid,asiEkleName.getText().toString(),formatliTarih,alertDialog);
                }
                else
                {
                    Toast.makeText(mContex,"Tarih seçiniz veya aşı ismi giriniz.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();
    }

    public void addAsi(String musid,String petid,String asiisim,String tarih,AlertDialog alertDialog)
    {
        Call<AsiEkleModel> servis = ManagerAll.getOurInstance().asiEkle(musid, petid, asiisim, tarih);
        servis.enqueue(new Callback<AsiEkleModel>() {
            @Override
            public void onResponse(Call<AsiEkleModel> call, Response<AsiEkleModel> response) {
               alertDialog.cancel();
               Toast.makeText(mContex,response.body().getText(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AsiEkleModel> call, Throwable t) {
                Toast.makeText(mContex, Warnings.internetProblemText,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Silme işlemi için alert dialog açma metodu
    public void openPetSilAlert(int position)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.pet_sil_alert,null);

        Button petSilEvet = view.findViewById(R.id.petSilEvet);
        Button petSilIptal = view.findViewById(R.id.petSilIptal);



        AlertDialog.Builder alert = new AlertDialog.Builder(mContex);
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

        petSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petSil(list.get(position).getPetid().toString(),position);
                alertDialog.cancel();
            }
        });

        petSilIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    public void petSil(String id,int position)
    {
        Call<PetSilModel> servis = ManagerAll.getOurInstance().petSil(id);
        servis.enqueue(new Callback<PetSilModel>() {
            @Override
            public void onResponse(Call<PetSilModel> call, Response<PetSilModel> response) {
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
            public void onFailure(Call<PetSilModel> call, Throwable t) {
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
