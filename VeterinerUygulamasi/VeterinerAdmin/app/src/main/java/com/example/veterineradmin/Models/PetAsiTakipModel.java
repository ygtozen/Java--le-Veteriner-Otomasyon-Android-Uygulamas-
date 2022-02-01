package com.example.veterineradmin.Models;

public class PetAsiTakipModel{
	private boolean tf;
	private String petresim;
	private String pettur;
	private String asitarih;
	private String telefon;
	private String asiisim;
	private String kadi;
	private String petisim;
	private String petcins;
	private String asiid;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPetresim(String petresim){
		this.petresim = petresim;
	}

	public String getPetresim(){
		return petresim;
	}

	public void setPettur(String pettur){
		this.pettur = pettur;
	}

	public String getPettur(){
		return pettur;
	}

	public void setAsitarih(String asitarih){
		this.asitarih = asitarih;
	}

	public String getAsitarih(){
		return asitarih;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setAsiisim(String asiisim){
		this.asiisim = asiisim;
	}

	public String getAsiisim(){
		return asiisim;
	}

	public void setKadi(String kadi){
		this.kadi = kadi;
	}

	public String getKadi(){
		return kadi;
	}

	public void setPetisim(String petisim){
		this.petisim = petisim;
	}

	public String getPetisim(){
		return petisim;
	}

	public void setPetcins(String petcins){
		this.petcins = petcins;
	}

	public String getPetcins(){
		return petcins;
	}

	public void setAsiid(String asiid){
		this.asiid = asiid;
	}

	public String getAsiid(){
		return asiid;
	}

	@Override
 	public String toString(){
		return 
			"PetAsiTakipModel{" + 
			"tf = '" + tf + '\'' + 
			",petresim = '" + petresim + '\'' + 
			",pettur = '" + pettur + '\'' + 
			",asitarih = '" + asitarih + '\'' + 
			",telefon = '" + telefon + '\'' + 
			",asiisim = '" + asiisim + '\'' + 
			",kadi = '" + kadi + '\'' + 
			",petisim = '" + petisim + '\'' + 
			",petcins = '" + petcins + '\'' + 
			",asiid = '" + asiid + '\'' + 
			"}";
		}
}
