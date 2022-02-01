<?php 

include("ayar.php");

$id = $_POST["id"]; // Bir id gönderdik.

// veteriner_takipasi tablosunda bizim gönderdiğimiz id ile eşleşen aşının durumunu 1 yapan sorgu.
$asiiptal = mysqli_query($baglan,"delete from veteriner_takipasi where id = '$id' ");

// Bir class oluştuduk.
class Result
{
    public $text;
    public $tf;
}
// Class'a ait bir nesne oluştuduk.
$result = new Result();

$result->text = "Asi iptal edildi.";
$result->tf = true;
echo(json_encode($result));


?>