<?php 

include("ayar.php");

// Normalde durumu 1 olanları listelememiz gerekiyor ama email onaylama olmadığı için 0 olanları listeliyoruz.
$sql = mysqli_query($baglan,"select * from veteriner_kullanicilar where durum = '0' ");

$count = mysqli_num_rows($sql);

// Bir class oluşturduk.
class Kullanicilar
{
    public $id;
    public $kadi;
    public $telefon;
    public $tf;
}

// Class'a ait bir nesne oluşturduk.
$kl = new Kullanicilar();

$sayac = 0;

if ($count > 0) // Müşterinin pet'i varsa
{
    echo("[");
    while ($bilgi = mysqli_fetch_assoc($sql))
    {
        $sayac = $sayac + 1;
        $kl->id = $bilgi["id"];
        $kl->kadi = $bilgi["kadi"]; // Veritabanıyla aynı isim olması gerek
        $kl->telefon = $bilgi["telefon"];
        $kl->tf = true;
        
        echo(json_encode($kl));
        
        if ($count > $sayac)
        {
            echo(",");
        }
    }
    echo("]");
    
}
else
{
        echo("[");
        $kl->id = null;
        $kl->kadi = null;
        $kl->telefon = null;
        $kl->tf = false;
        
        echo(json_encode($kl));
        
        echo("]");
}

?>





