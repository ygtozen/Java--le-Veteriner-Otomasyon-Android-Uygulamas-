<?php 

include("ayar.php")

$mailAdres = $_GET["mail"];
$kod = $_GET["dogrulamaKodu"];

$guncelle = mysqli_query($baglan,"update veteriner_kullanicilar set durum = '1' where mail = '$mailAdres' and dogrulamaKodu = '$kod' ");


if ($guncelle)
{
    
?>
<h1>TEBRİKLER HESABINIZ DOĞRULANDI....</h1>

<?php
    
}


?>