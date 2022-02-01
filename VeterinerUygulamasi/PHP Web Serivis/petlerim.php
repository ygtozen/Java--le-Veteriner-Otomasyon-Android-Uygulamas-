<?php 

include("ayar.php");

$mus_id = $_POST["musid"];

// Mobil taraftan girilen müşteri id ile tabloda kayıtlı müşteri id'si aynı olan
$sorgula = mysqli_query($baglan,"select * from veteriner_pet_listesi where 	musteri_id = '$mus_id' ");

// Sayısını aldık
$count = mysqli_num_rows($sorgula);

// Bir class oluşturduk.
class PetClass
{
    public $petid;
    public $petisim;
    public $pettur;
    public $petcins;
    public $petresim;
    public $tf;
}

// Class'a ait bir nesne oluşturduk.
$pet = new PetClass();

$sayac = 0;

if ($count > 0) // Müşterinin pet'i varsa
{
    echo("[");
    while ($bilgi = mysqli_fetch_assoc($sorgula))
    {
        $sayac = $sayac + 1;
        $pet->petid = $bilgi["id"];
        $pet->petisim = $bilgi["pet_isim"]; // Veritabanıyla aynı isim olması gerek
        $pet->pettur = $bilgi["pet_tur"];
        $pet->petcins = $bilgi["pet_cins"];
        $pet->petresim = $bilgi["pet_resim"];
        $pet->tf = true;
        
        echo(json_encode($pet));
        
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
        $pet->petid = null;
        $pet->petisim = null;
        $pet->pettur = null;
        $pet->petcins = null;
        $pet->petresim = null;
        $pet->tf = false;
        
        echo(json_encode($pet));
        
        echo("]");
}




?>







