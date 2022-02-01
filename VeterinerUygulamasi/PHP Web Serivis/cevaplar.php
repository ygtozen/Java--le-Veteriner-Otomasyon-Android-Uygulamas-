<?php 

include("ayar.php");

$musteri_id = $_POST["mus_id"];

// SQL JOIN 2 TABLOYU BİR BİRİNE BAĞLADIK
$sor = mysqli_query($baglan,"SELECT a.id as soruid ,a.soru, b.cevap, b.id as cevapid ,b.soru_id
FROM veteriner_sorular a 
INNER JOIN veteriner_cevaplar b ON a.id = b.soru_id WHERE a.durum = 1 and a.musteri_id = '$musteri_id' and b.musteri_id = '$musteri_id' ");

// Sayısını aldık
$count = mysqli_num_rows($sor);


// Bir class oluşturduk.
class soruClass
{
    public $soruid;
    public $soru;
    public $cevap;
    public $cevapid;
    public $tf;
}

// Class'a ait bir nesne oluşturduk.
$soru = new soruClass();

$sayac = 0;

if ($count > 0) // Müşterinin pet'i varsa
{
    echo("[");
    while ($bilgi = mysqli_fetch_assoc($sor))
    {
        $sayac = $sayac + 1;
        $soru->soru = $bilgi["soru"];
        $soru->cevap = $bilgi["cevap"]; // Veritabanıyla aynı isim olması gerek
        $soru->soruid = $bilgi["soruid"];
        $soru->cevapid = $bilgi["cevapid"];
        $soru->tf = true;
        
        echo(json_encode($soru));
        
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
        $soru->soru = null;
        $soru->cevap = null; // Veritabanıyla aynı isim olması gerek
        $soru->soruid = null;
        $soru->cevapid = null;
        $soru->tf = false;
        
        echo(json_encode($soru));
        
        echo("]");
}



?>