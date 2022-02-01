<?php 

include("ayar.php");

$cevap_id = $_POST["cevap"];
$soru_id = $_POST["soru"];

$sil = mysqli_query($baglan,"delete from veteriner_cevaplar where id = '$cevap_id' and soru_id = '$soru_id' ");

$sil2 = mysqli_query($baglan,"delete from veteriner_sorular where id = '$soru_id' ");

class DeleteRecord
{
    public $text;
    public $tf;
}

$del = new DeleteRecord();

//if ($sil && $sil2)
//{
    $del->text = "Silme işlemi başarıyla gerçekleşti.";
    $del->tf = true;
    echo(json_encode($del));
/*}
else
{
    $del->text = "Silme işlemi gerçekleşmedi.";
    $del->tf = false;
    echo(json_encode($del));
}*/



?>





