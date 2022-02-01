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

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getOurInstance()
    {
        return ourInstance;
    }

    // Kampanyaları Listeleme
    public Call<List<KampanyaModel>> getKampanya()
    {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return x;
    }

    // Kampanya Ekle
    public Call<KampanyaEkleModel> addKampanya(String baslik, String text, String resim)
    {
        Call<KampanyaEkleModel> y = getRestApi().addKampanya(baslik, text, resim);
        return y;
    }

    // Kampanya Silme
    public Call<KampanyaSilModel> kampanyaSil(String kampanya_id)
    {
        Call<KampanyaSilModel> x = getRestApi().kampanyaSil(kampanya_id);
        return x;
    }

    // Bu gün aşılanacak petlerin listelenmesi
    public Call<List<PetAsiTakipModel>> getAsiPet(String tarih)
    {
        Call<List<PetAsiTakipModel>> z = getRestApi().getPetAsiTakip(tarih);
        return z;
    }

    // Aşı onaylama
    public Call<AsiOnaylaModel> asiOnayla(String id)
    {
        Call<AsiOnaylaModel> x = getRestApi().asiOnayla(id);
        return x;
    }

    // Aşı iptal etme
    public Call<AsiOnaylaModel> asiIptal(String id)
    {
        Call<AsiOnaylaModel> x = getRestApi().asiIptal(id);
        return x;
    }

    // Soruları listeleme
    public Call<List<SoruModel>> getSoru()
    {
        Call<List<SoruModel>> x = getRestApi().getSoru();
        return x;
    }

    // Veteriner soru cevaplama işlemi
    public Call<CevaplaModel> cevapla(String musteriid, String soruid, String text)
    {
        Call<CevaplaModel> x = getRestApi().cevapla(musteriid, soruid, text);
        return x;
    }

    // Kullanıcıları listeleme
    public Call<List<KullanicilarModel>> getKullanici()
    {
        Call<List<KullanicilarModel>> x = getRestApi().getKullanicilar();
        return x;
    }

    // Kullanıcıları pet listeleme
    public Call<List<PetModel>> getPets(String musid)
    {
        Call<List<PetModel>> x = getRestApi().getPets(musid);
        return x;
    }

    // Pet ekleme işlemi
    public Call<PetEkleModel> petEkle(String musteriid, String isim, String tur, String cins, String resim)
    {
        Call<PetEkleModel> x = getRestApi().petEkle(musteriid, isim, tur, cins, resim);
        return x;
    }

    // Aşı Ekle
    public Call<AsiEkleModel> asiEkle(String musid, String petid, String asiisim, String tarih)
    {
        Call<AsiEkleModel> x = getRestApi().asiEkle(musid, petid, asiisim, tarih);
        return x;
    }

    // Kullanici Silme
    public Call<KullaniciSilModel> kullanciSil(String id)
    {
        Call<KullaniciSilModel> x = getRestApi().kullaniciSil(id);
        return x;
    }

    // Pet Silme
    public Call<PetSilModel> petSil(String id)
    {
        Call<PetSilModel> x = getRestApi().petSil(id);
        return x;
    }


}
