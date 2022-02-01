<?php 

include("ayar.php");

$musteri_id = $_POST["musteriid"];
$soru_id = $_POST["soruid"];
$text = $_POST["text"];

// Ekleme sorgusu
$ekle = mysqli_query($baglan,"insert into veteriner_cevaplar (musteri_id,soru_id,cevap) values ('$musteri_id','$soru_id','$text') ");

// Bir class oluştuduk.
class Result
{
    public $text;
    public $tf;
}
// Class'a ait bir nesne oluşturduk.
$res = new Result();

if ($ekle)
{
    // veteriner_sorular tablosundan durumu 1 yapıcaz
    $guncelle = mysqli_query($baglan,"update veteriner_sorular set durum = '1' where id = '$soru_id' ");
    
    $res->text = "Soru cevaplandı.";
    $res->tf = true;
    echo(json_encode($res));
}
else
{
    $res->text = "Soru cevaplanamadı.";
    $res->tf = false;
    echo(json_encode($res));
}

?>


