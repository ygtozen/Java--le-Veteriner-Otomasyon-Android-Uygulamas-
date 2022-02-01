<?php 

include("ayar.php");

$musid = $_POST["musid"]; // Gönderdiğimiz parametreler
$petid = $_POST["petid"];
$asi = $_POST["asiisim"];
$tarih = $_POST["tarih"];

// Ekleme sorgusu
$ekle = mysqli_query($baglan,"insert into veteriner_takipasi (musteri_id,pet_id,asi_isim,asi_durum,asi_tarih) values ('$musid','$petid','$asi','0','$tarih') ");

class Result
{
    public $text;
    public $tf;
}

$res = new Result();

if ($ekle) // Ekleme işlemi başarılı ise
{
    $res->text = "Pete asi eklenmistir.";
    $res->tf = true;
    echo(json_encode($res));
}
else
{
    $res->text = "Pete asi ekleme basarisiz.";
    $res->tf = false;
    echo(json_encode($res));
}



?> 
