<?php 

include("ayar.php");

$id = $_POST["id"];

$sil = mysqli_query($baglan,"delete from veteriner_kullanicilar where id = '$id' ");

class Result
{
    public $text;
    public $tf;
}

$res = new Result();

if ($sil)
{
    $res->text = "Silme islemi basarili";
    $res->tf = true;
    echo(json_encode($res));
}
else
{
    $res->text = "Silme islemi basarisiz.";
    $res->tf = false;
    echo(json_encode($res));
}

?>