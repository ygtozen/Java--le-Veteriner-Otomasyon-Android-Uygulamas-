<?php 

include("ayar.php");

$mailAdres = $_POST["mailAdres"];
$sifre = $_POST["sifre"];

// Bizim mobil taraftan girdiğimiz mail ve şifre tabloda kayıtlımı diye bakar
$kontrol = mysqli_query($baglan,"select * from veteriner_kullanicilar where mail = '$mailAdres' and parola = '$sifre' ");

// Sayısını alır
$count = mysqli_num_rows($kontrol);

class UserLogin // Bir class oluşturduk
{
    public $id;
    public $username;
    public $mailadres;
    public $parola;
    public $tf;
    public $text;
}

$user = new UserLogin();

if ($count > 0) // Eğer count 0 dan büyükse böyle bir kullancı var
{
    $parse = mysqli_fetch_assoc($kontrol);
    $durum = $parse["durum"];
    $id = $parse["id"];
    $username = $parse["kadi"];
    $parola = $parse["parola"];
    $mailadres = $parse["mail"];
    
    if ($durum == 0) // Mail onaylama olmadığı için 0 yazdık yoksa 1 olacaktı
    {
        $user->tf = true;
        $user->text = "Sisteme giriş başarılı bir şekilde gerçekleşti.";
        $user->id = $id;
        $user->parola = $parola;
        $user->username = $username;
        $user->mailadres = $mailadres;
        
        echo(json_encode($user));
    }
    else
    {
        $user->tf = false;
        $user->text = "Sisteme giriş yapabilmeniz için mail adresinizi onaylamanız gerekmektedir.";
        $user->id = null;
        $user->parola = null;
        $user->username = null;
        $user->mailadres = null;
        
        echo(json_encode($user));
    }
}
else // Böyle bir kullanıcı yok
{
    $user->tf = false;
    $user->text = "Girmiş olduğunuz kullanıcı sistemde kayıtlı değildir.";
    $user->id = null;
    $user->parola = null;
    $user->username = null;
    $user->mailadres = null;
    
    echo(json_encode($user));
}



?>



