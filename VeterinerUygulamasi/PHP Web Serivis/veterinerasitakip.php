<?php 

include("ayar.php");

$tarih = $_POST["tarih"];

// SQL Sorgusunu hazırladık.
$listele = mysqli_query($baglan,"SELECT a.pet_isim, a.pet_tur, a.pet_cins, a.pet_resim, b.asi_isim,b.asi_tarih,b.id as asiid ,c.kadi,c.telefon FROM veteriner_pet_listesi a INNER JOIN veteriner_takipasi b on a.id = b.pet_id INNER JOIN veteriner_kullanicilar c on c.id = b.musteri_id  WHERE b.asi_durum = 0 and b.asi_tarih = '$tarih' ");


// Bir class oluşturduk.
class Takip
{
    public $petisim;
    public $pettur;
    public $petcins;
    public $petresim;
    public $asiisim;
    public $asitarih;
    public $asiid;
    public $kadi;
    public $telefon;
    public $tf = true ;
}

// Class'a ait bir nesne oluşturduk.
$takip = new Takip();

$count = mysqli_num_rows($listele);

$sayac = 0;

if ($count > 0) // 0'dan büyük ise kampanya var demektir
{
    echo("[");
    while($kayit = mysqli_fetch_assoc($listele))
    {
        $sayac = $sayac + 1;
        $takip->petisim = $kayit["pet_isim"]; //$kayit içindeki isimler veritabanıyla aynı olamk zorunda
        $takip->pettur = $kayit["pet_tur"];
        $takip->petcins = $kayit["pet_cins"];
        $takip->petresim = $kayit["pet_resim"];
        $takip->asiisim = $kayit["asi_isim"];
        $takip->asitarih = $kayit["asi_tarih"];
        $takip->asiid = $kayit["asiid"];
        $takip->kadi = $kayit["kadi"];
        $takip->telefon = $kayit["telefon"];
        $takip->tf = true;
        echo(json_encode($takip));
        
        if ($count > $sayac)
        {
            echo(",");
        }
            
    }
    echo("]");
}
else //0'dan küçük ise kampanya yok demektir
{
        echo("[");
        $takip->petisim = null; //$kayit içindeki isimler veritabanıyla aynı olamk zorunda
        $takip->pettur = null;
        $takip->petcins = null;
        $takip->petresim = null;
        $takip->asiisim = null;
        $takip->asitarih = null;
        $takip->asiid = null;
        $takip->kadi = null;
        $takip->telefon = null;
        $takip->tf = false;
        echo(json_encode($takip));
        echo("]");
}


?>
