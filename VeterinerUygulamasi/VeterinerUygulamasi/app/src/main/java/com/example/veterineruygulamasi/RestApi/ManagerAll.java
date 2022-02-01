package com.example.veterineruygulamasi.RestApi;

import com.example.veterineruygulamasi.Fragments.AnswerModel;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.Models.AskQuestionModel;
import com.example.veterineruygulamasi.Models.DeleteAnswerModel;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.Models.LoginModel;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getOurInstance()
    {
        return ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mail, String kadi, String parola)
    {
        Call<RegisterPojo> x = getRestApi().registerUser(mail, kadi, parola);
        return x;
    }

    public Call<LoginModel> girisYap(String mail, String parola)
    {
        Call<LoginModel> y = getRestApi().loginUser(mail, parola);
        return y;
    }

    public Call<List<PetModel>> getPets(String mus_id)
    {
        Call<List<PetModel>> z = getRestApi().getPets(mus_id);
        return z;
    }

    // soru
    public Call<AskQuestionModel> soruSor(String id, String soru)
    {
        Call<AskQuestionModel> s = getRestApi().soruSor(id, soru);
        return s;
    }

    // Cevap
    public Call<List<AnswerModel>> getAnswer(String mus_id)
    {
        Call<List<AnswerModel>> cevap = getRestApi().getAnswer(mus_id);
        return cevap;
    }

    // Sil
    public Call<DeleteAnswerModel> deleteAnswer(String cevap, String soru)
    {
        Call<DeleteAnswerModel> sil = getRestApi().deleteAnswer(cevap, soru);
        return sil;
    }

    // Kampanya listele
    public Call<List<KampanyaModel>> getKampanya()
    {
        Call<List<KampanyaModel>> kampanya = getRestApi().getKampanya();
        return kampanya;
    }

    // Aşı Takip
    public Call<List<AsiModel>> getAsiTakip(String id)
    {
        Call<List<AsiModel>> asi = getRestApi().getAsiTakip(id);
        return asi;
    }

    // Geçimil aşı
    public Call<List<AsiModel>> getGecmisAsi(String id, String petid)
    {
        Call<List<AsiModel>> g_asi = getRestApi().getGecmisAsi(id, petid);
        return g_asi;
    }

}
