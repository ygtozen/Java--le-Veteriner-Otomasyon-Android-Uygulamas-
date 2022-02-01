<?php 

include("ayar.php");

// veteriner_sorular tablosundan musteri_id ile veteriner_kullanicilar tablosundan id'yi birleştirdi ve soru durumu 0 olan listeleme sorgusu
$listele = mysqli_query($baglan,"SELECT a.musteri_id as musteriid ,a.soru,b.kadi,b.telefon,a.id as soruid FROM veteriner_sorular a INNER JOIN veteriner_kullanicilar b on a.musteri_id = b.id where a.durum = '0' ");

$count = mysqli_num_rows($listele);
// Bir class oluşturuduk.
class Sorular
{
    public $musteriid;
    public $soru;
    public $kadi;
    public $telefon;
    public $soruid;
    public $tf;
}
// Class'a ait bir nesne oluşturduk.
$soru = new Sorular();

$sayac = 0;

if ($count > 0) // Eğer soru varsa
{
    echo("[");
    while($bilgi = mysqli_fetch_assoc($listele))
    {
        $sayac = $sayac + 1;
        $soru->musteriid = $bilgi["musteriid"];// bilgi içindeki parametreler veritabanıyla aynı olmalıdır.
        $soru->soru = $bilgi["soru"];
        $soru->kadi = $bilgi["kadi"];
        $soru->telefon = $bilgi["telefon"];
        $soru->soruid = $bilgi["soruid"];
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
    echo("{");
        $soru->musteriid = null;
        $soru->soru = null;
        $soru->kadi = null;
        $soru->telefon = null;
        $soru->soruid = null;
        $soru->tf = false;
        echo(json_encode($soru));
    echo("]");
}



?>

