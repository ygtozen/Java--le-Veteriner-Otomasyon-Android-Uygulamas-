<?php 

include("ayar.php");

$baslik = $_POST["baslik"]; // Mobil taraftan bir başlık giricez.
$text = $_POST["text"]; // Mobil taraftan bir text giricez.
$resim = $_POST["resim"]; // Mobil taraftan bir resim ekliycez.

class Result // Bir class oluşturudu.
{
    public $sonuc;
    public $tf;
}

$result = new Result(); // Class'a ait bir nesne oluşturduk.
$isim = rand(0,100000).rand(0,100000).rand(0,100000); //Resime rastgele sayılardan bir isim oluşturacak.

$yol = "resimler/$isim.jpg"; // Resmin nereye ve hangi isimle kayıt edileceğini belirttik.

$resimpath = "http://yigitozen98.tk/veterinerservis/resimler/$isim.jpg";

//Veritabanına ekleme sorgusu
$add = mysqli_query($baglan,"insert into veteriner_kampanyalar (text,resim,baslik) values ('$text','$resimpath','$baslik') ");

if ($add)
{
    file_put_contents($yol,base64_decode($resim));
    $result->sonuc = "Kampanya Eklendi";
    $result->tf = true;
    echo(json_encode($result));
}
else
{
    $result->sonuc = "Kampanya eklenmedi.";
    $result->tf = false;
    echo(json_encode($result));
}


?>







