<?php 

include("ayar.php");

// Bir id göndericez.
$id = $_POST["id"];

// Gönderdiğimiz id ile tablodaki id aynı olan kayıtı silme sorgusu.
$sil = mysqli_query($baglan,"delete from veteriner_kampanyalar where id = '$id' ");

// Bir class tanımladık.
class Result
{
    public $text;
    public $tf;
}

// Class'a ait bir nesne tanımladık.
$result = new Result();

if ($sil) // Silme işlemi başarılı ise
{
    $result->text = "Kampanya silme işlemi başarılı.";
    $result->tf = true;
    echo(json_encode($result)); // Json formatında yazdırma.
}
else // Değilse
{
    $result->text = "Kampanya silinemedi.";
    $result->tf = false;
    echo(json_encode($result)); // Json formatında yazdırma.
}


?>









