package com.example.veterineradmin.RestApi;

import com.example.veterineradmin.Models.AsiEkleModel;
import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.Models.PetEkleModel;
import com.example.veterineradmin.Models.PetModel;
import com.example.veterineradmin.Models.PetSilModel;
import com.example.veterineradmin.Models.SoruModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    // Kampanya Listeleme
    @GET("/veterinerservis/kampanyaidli.php")
    Call<List<KampanyaModel>> getKampanya();

    // Kampanya ekleme
    @FormUrlEncoded
    @POST("/veterinerservis/kampanyaekle.php")
    Call<KampanyaEkleModel> addKampanya(@Field("baslik") String baslik, @Field("text") String text
                                        ,@Field("resim") String resim);

    // Kampanya Silme
    @FormUrlEncoded
    @POST("/veterinerservis/kampanyasil.php")
    Call<KampanyaSilModel> kampanyaSil(@Field("id") String kampanya_id);

    // Petlerin listelenmesi için tarih işlemi
    // Bu gün aşılanacak petlerin listelenmesi
    @FormUrlEncoded
    @POST("/veterinerservis/veterinerasitakip.php")
    Call<List<PetAsiTakipModel>> getPetAsiTakip(@Field("tarih") String tarih);

    // Aşı onaylama
    @FormUrlEncoded
    @POST("/veterinerservis/asionayla.php")
    Call<AsiOnaylaModel> asiOnayla(@Field("id") String kampanya_id);

    // Aşı iptal etme
    @FormUrlEncoded
    @POST("/veterinerservis/asiiptal.php")
    Call<AsiOnaylaModel> asiIptal(@Field("id") String kampanya_id);

    // Soruları listeleme
    @GET("/veterinerservis/sorular.php")
    Call<List<SoruModel>> getSoru();

    // Veterinerin cevaplama işlemi
    @FormUrlEncoded
    @POST("/veterinerservis/veterinercevapla.php")
    Call<CevaplaModel> cevapla(@Field("musteriid") String musteriid, @Field("soruid") String soruid
            , @Field("text") String text);

    // Kullanıcılar listeleme
    @GET("/veterinerservis/kullanicilar.php")
    Call<List<KullanicilarModel>> getKullanicilar();

    // Kullanıcı pet listeleme
    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String musid);

    // Pet ekleme
    @FormUrlEncoded
    @POST("/veterinerservis/petekle.php")
    Call<PetEkleModel> petEkle(@Field("musid") String musid, @Field("isim") String isim
            , @Field("tur") String tur, @Field("cins") String cins
            , @Field("resim") String resim);

    // Aşı ekleme
    @FormUrlEncoded
    @POST("/veterinerservis/asiekle.php")
    Call<AsiEkleModel> asiEkle(@Field("musid") String musid, @Field("petid") String petid
            , @Field("asiisim") String asiisim, @Field("tarih") String tarih);

    // Kullanici Silme
    @FormUrlEncoded
    @POST("/veterinerservis/kullanicisil.php")
    Call<KullaniciSilModel> kullaniciSil(@Field("id") String id);

    // Pet Silme
    @FormUrlEncoded
    @POST("/veterinerservis/petsil.php")
    Call<PetSilModel> petSil(@Field("id") String id);

}
