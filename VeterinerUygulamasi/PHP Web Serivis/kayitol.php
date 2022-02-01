<?php 

include("ayar.php");

$mailAdres = $_POST["mail"];
$kadi = $_POST["kadi"];
$parola = $_POST["parola"];
$dogrulamaKodu = rand(10000,100000);
$durum = 0;

// Bizim girdiğimiz mail ve kullanici adi tabloda kayıtlımı diye bakar
$kontrol = mysqli_query($baglan,"select * from veteriner_kullanicilar where mail = '$mailAdres' or kadi = '$kadi' ");
// Sayısını alır
$kontrolCount = mysqli_num_rows($kontrol);

class User // Bir class oluşturduk
{
    public $text;
    public $tf;
}

$user = new User(); // Class'a ait bir nesne oluşturduk.

if ($kontrolCount > 0)
{
    $user->text = "Girmiş olduğunuz bilgilere ait kullanıcı bulunmaktadır. Lütfen bilgileri kontrol edin.";
    $user->tf = false;
    echo(json_encode($user));
}
else
{
    $addUser = mysqli_query($baglan,"insert into veteriner_kullanicilar (kadi,mail,parola,dogrulamaKodu,durum) values ('$kadi','$mailAdres','$parola','$dogrulamaKodu','$durum') ");
    
    $git = "http://yigitozen98.tk/veterinerservis/aktifet.php?mail=".$mailAdres."&dogrulamaKodu=".$dogrulamaKodu ;
    
    $to = $mailAdres;
    $subject = "Kulllanıcı Hesabı Aktif Etme";
    
    $text = "Merhaba Sayın ".$kadi." \n Sisteme giriş yapabilmek için onayınız gerekmektedir. <a href='".$git."'> Onayla </a> "  ;
    
    $from = "From: ozenyigitybsgmail.com@yigitozen98.tk";
    $from .= "MIME-Version: 1.0\r\n";
    $from .= "Content-Type: text/html; charset=UTF-8\r\n";
    mail($to,$subject,$text,$from);
    
    $user->text = "Hesabınız kayıt edildi mail adresinizi kontrol edebilirsiniz.";
    $user->tf = true;
    echo(json_encode($user));
  
}



?>





