<?php 

include("ayar.php");

$code = rand(10000,1000000);

$ad = "yigit"; //$_POST["kullaniciAdi"];
$sifre = "1234"; $_POST["sifre"];
$mailadres = "ozenyigit151@gmail.com"; //$_POST["mailadres"];
$durum = 0;

$kontrol = mysqli_query($baglan,"select * from ekle where uyeadi = '$ad' or mailadres = '$mailadres' ");
$sayi = mysqli_num_rows($kontrol);

if ($sayi > 0)
{
    
}
else
{
    $ekle = mysqli_query($baglan,"insert into ekle (uyeadi,sifre,dogrulamakodu,durum,mailadres) values ('$ad','$sifre','$code','$durum','$mailadres')");
    if ($ekle)
    {
        $to = $mailadres; //kime gonderilecek.
        $subject = "Mail doğrulamanız gerekiyor."; //konu-baslik
        $message = "Gidecek olan mesaj"; //mesaj
        
        $sender = "From: <ozenyigitybs@gmail.com>"; // gonderen kim gonderiyor
        $gonder = mail($to,$subject,$message,$sender);
        if  ($gonder)
        {
            $x = (array('result' => true));
            echo json_encode($x);
        }
    }
}




?>






