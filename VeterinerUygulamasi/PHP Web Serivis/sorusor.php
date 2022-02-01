<?php 

include("ayar.php");

$mus_id = $_POST["id"];
$soru = $_POST["soru"];

// Sorulan soru veteriner_sorular tablosuna eklenir
$ekle = mysqli_query($baglan,"insert into veteriner_sorular (musteri_id,soru,durum) values ('$mus_id','$soru','0') ");

class Ekleme
{
    public $text;
    public $tf;
}

$ekleme = new Ekleme();

if ($ekle) // Ekleme işlemi gerçekleşmiş ise
{
    $ekleme->text = "Sorunuz ilgili veterinere ulaşmıştır. Cevabını bir zaman sonra cevaplar alanından görebilirsiniz.";
    $ekleme->tf = true;
    echo(json_encode($ekleme));
}
else
{
    $ekleme->text = "Sorunuz gönderilirken bir hata meydana gelmiştir. Daha sonra tekrar deneyin.";
    $ekleme->tf = false;
    echo(json_encode($ekleme));
}


?>