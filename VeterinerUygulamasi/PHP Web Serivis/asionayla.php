<?php 

include("ayar.php");

$id = $_POST["id"]; // Bir id gönderdik.

// veteriner_takipasi tablosunda bizim gönderdiğimiz id ile eşleşen aşının durumunu 1 yapan sorgu.
$onayla = mysqli_query($baglan,"update veteriner_takipasi set asi_durum = '1' where id = '$id' ");

// Bir class oluştuduk.
class Result
{
    public $text;
    public $tf;
}
// Class'a ait bir nesne oluştuduk.
$result = new Result();

$result->text = "Asi yapilmistir.";
$result->tf = true;
echo(json_encode($result));


?>