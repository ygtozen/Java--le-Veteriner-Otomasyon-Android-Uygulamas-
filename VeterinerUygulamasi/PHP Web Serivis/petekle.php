<?php 

include("ayar.php");

$musteri_id = $_POST["musid"];
$pet_isim = $_POST["isim"];
$tur = $_POST["tur"];
$cins = $_POST["cins"];
$resim = $_POST["resim"];

class Result
{
    public $text;
    public $tf;
}

$res = new Result();

$isim = rand(0,100000).rand(0,100000).rand(0,100000).rand(0,100000); //resmin ismi

$yol = "resimler/$isim.jpg";
$resimurl = "http://yigitozen98.tk/veterinerservis/resimler/$isim.jpg";

// Ekleme sorgusu
$ekle = mysqli_query($baglan,"insert into veteriner_pet_listesi (musteri_id,pet_isim,pet_tur,pet_cins,pet_resim) values ('$musteri_id','$pet_isim','$tur','$cins','$resimurl') ");

if ($ekle)
{
    file_put_contents($yol,base64_decode($resim));// resmi base64 stringe çevirip $yol içine koyuyoruz.
    
    $res->text = "Pet ekleme başarılı.";
    $res->tf = true;
    echo(json_encode($res));
}
else
{
    $res->text = "Pet ekleme işlemi başarısız.";
    $res->tf = false;
    echo(json_encode($res));
}


?>

	