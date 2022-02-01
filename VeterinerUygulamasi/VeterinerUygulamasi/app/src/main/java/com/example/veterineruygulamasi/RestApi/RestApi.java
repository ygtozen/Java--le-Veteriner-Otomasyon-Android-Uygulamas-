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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @FormUrlEncoded
    @POST("/veterinerservis/kayitol.php")
    Call<RegisterPojo> registerUser(@Field("mail") String mail, @Field("kadi") String kadi, @Field("parola") String parola);

    @FormUrlEncoded
    @POST("/veterinerservis/girisyap.php")
    Call<LoginModel> loginUser(@Field("mailAdres") String mailAdres, @Field("sifre") String sifre);

    // Kullanıcıların pet'lerini listeleme
    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id);

    // Veterinere soru sorma
    @FormUrlEncoded
    @POST("/veterinerservis/sorusor.php")
    Call<AskQuestionModel> soruSor(@Field("id") String id, @Field("soru") String soru);

    // Cevap işlemleri
    @FormUrlEncoded
    @POST("/veterinerservis/cevaplar.php")
    Call<List<AnswerModel>> getAnswer(@Field("mus_id") String mus_id);

    // Silme işlemi
    @FormUrlEncoded
    @POST("/veterinerservis/cevapsil.php")
    Call<DeleteAnswerModel> deleteAnswer(@Field("cevap") String cevap, @Field("soru") String soru);

    // Kampanya Listeleme
    @GET("/veterinerservis/kampanya.php")
    Call<List<KampanyaModel>> getKampanya();

    // Aşı Takip
    @FormUrlEncoded
    @POST("/veterinerservis/asitakip.php")
    Call<List<AsiModel>> getAsiTakip(@Field("id") String id);

    // Geçmiş aşı takibi - yapılan aşılar
    @FormUrlEncoded
    @POST("/veterinerservis/gecmisasi.php")
    Call<List<AsiModel>> getGecmisAsi(@Field("id") String id, @Field("petid") String petid);

}
