package com.example.veterineruygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamasi.Models.RegisterPojo;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {

    private Button btn_kayitol;
    private EditText registerMail , registerUsername , registerPassword;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        tanimla();
        registerToUser();
        changeActivity();
    }

    public void tanimla()
    {
        btn_kayitol = findViewById(R.id.btn_kayitol);
        registerMail = findViewById(R.id.registerMail);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerText = findViewById(R.id.registerText);
    }

    public void registerToUser()
    {
        btn_kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = registerMail.getText().toString();
                String username = registerUsername.getText().toString();
                String parola = registerPassword.getText().toString();
                register(mail,username,parola);

                delete();
            }
        });
    }

    public void changeActivity()
    {
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KayitOlActivity.this , GirisYapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void delete()
    {
        registerMail.setText("");
        registerUsername.setText("");
        registerPassword.setText("");
    }

    public void register(String userMailAdres, String userName, String userParola)
    {
        Call<RegisterPojo> servis = ManagerAll.getOurInstance().kayitOl(userMailAdres,userName,userParola);
        servis.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(KayitOlActivity.this , GirisYapActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                // Hatalar buraya düşer
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}