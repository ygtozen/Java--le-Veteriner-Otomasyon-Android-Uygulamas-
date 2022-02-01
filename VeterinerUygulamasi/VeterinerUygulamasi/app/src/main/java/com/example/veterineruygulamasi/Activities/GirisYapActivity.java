package com.example.veterineruygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.MainActivity;
import com.example.veterineruygulamasi.Models.LoginModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.GetSharedPreferences;
import com.example.veterineruygulamasi.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {

    private EditText loginMail, loginParola;
    private Button btn_login;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        tanimla();
        action();
    }

    public void tanimla()
    {
        loginMail = findViewById(R.id.loginMail);
        loginParola = findViewById(R.id.loginParola);
        btn_login = findViewById(R.id.btn_login);
        loginText = findViewById(R.id.loginText);
    }

    public void action()
    {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = loginMail.getText().toString();
                String sifre = loginParola.getText().toString();
                login(mail, sifre);

                delete();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void delete()
    {
        loginMail.setText("");
        loginParola.setText("");
    }

    public void login(String mail, String parola)
    {
        Call<LoginModel> servis = ManagerAll.getOurInstance().girisYap(mail, parola);
        servis.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GirisYapActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    GetSharedPreferences getSharedPreferences = new GetSharedPreferences(GirisYapActivity.this);
                    getSharedPreferences.setSession(response.body().getId(),
                            response.body().getUsername(),response.body().getMailadres());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}