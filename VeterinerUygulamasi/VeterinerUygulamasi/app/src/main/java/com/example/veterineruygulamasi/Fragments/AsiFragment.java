package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragment;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiFragment extends Fragment {

    private View view;
    private CalendarPickerView calenderPickerView;
    private DateFormat dateFormat;
    private Calendar nextYear; // Hangi yıla kadar gösterilecek.
    private Date today;
    private List<AsiModel> asiList;
    private List<Date> dateList;
    private String  id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);

        tanimla();
        getAsi();
        clickToCalander();

        return view;
    }

    public void tanimla()
    {
        calenderPickerView = view.findViewById(R.id.calenderPickerView);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1); // 1 Yıl sonrasında kadar göster
        today = new Date();

        calenderPickerView.init(today,nextYear.getTime());

        asiList = new ArrayList<>();
        dateList = new ArrayList<>();

        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id",null);
    }

    public void getAsi()
    {
        Call<List<AsiModel>> servis = ManagerAll.getOurInstance().getAsiTakip(id);
        servis.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).isTf()) {
                        asiList = response.body();
                        for (int i = 0; i < asiList.size(); i++) {
                            String dateString = response.body().get(i).getAsitarih().toString();
                            try {
                                Date date = dateFormat.parse(dateString);
                                dateList.add(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        calenderPickerView.init(today, nextYear.getTime())
                                .withHighlightedDates(dateList);
                    }
                    else
                    {
                        ChangeFragment changeFragment = new ChangeFragment(getContext());
                        changeFragment.change(new HomeFragment());
                        Toast.makeText(getContext(),"Petinize ait gelecek tarihte aşı yoktur."
                                ,Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }

    public void clickToCalander()
    {
        calenderPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for (int i=0; i < dateList.size(); i++)
                {
                    if (date.toString().equals(dateList.get(i).toString()))
                    {
                        //Toast.makeText(getContext(),asiList.get(i).getPetisim(),Toast.LENGTH_LONG).show();
                        openDateAlert(asiList.get(i).getPetisim(),asiList.get(i).getAsitarih()
                        ,asiList.get(i).getAsiisim(),asiList.get(i).getPetresim());
                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void openDateAlert(String petisim, String tarih , String asiismi, String resimurl)
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.asi_takip_alert_layout,null);

        TextView txt_petisim = view.findViewById(R.id.txt_petisim);
        TextView txt_petasitakipbilgi = view.findViewById(R.id.txt_petasitakipbilgi);
        CircleImageView asitakip_image = view.findViewById(R.id.asitakip_image);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true); // Ekranın boş bir yerine basınca kapanır
        AlertDialog alertDialog =  alert.create();

       txt_petisim.setText(petisim);
       txt_petasitakipbilgi.setText(petisim + " isimli petinizin " + tarih + " tarhinde " + asiismi
               + " aşısı yapılacaktır." );

        Picasso.get().load(resimurl).into(asitakip_image);


        alertDialog.show();
    }
}