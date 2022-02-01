<?php

$serverName = "45.143.97.112";
$userName = "yigityms_hoca";
$sifre = "Bandirma10.5";
$dbName = "yigityms_yigit";

$baglan = mysqli_connect($serverName,$userName,$sifre,$dbName);

// Türkçe karakterler için
mysqli_set_charset($baglan,"UTF-8");
mysqli_query($baglan,"SET NAMES UTF8");

/*if ($baglan)
{
    echo("BAŞARILI");
}
else
{
    echo("BAŞARISIZ.");
}*/

?>