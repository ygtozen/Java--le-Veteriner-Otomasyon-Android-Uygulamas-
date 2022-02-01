package com.example.veterineruygulamasi.Models;

public class AsiModel{
	private boolean tf;
	private String petresim;
	private String pettur;
	private String asitarih;
	private String asiisim;
	private String petisim;
	private String petcins;

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

	public void setAsiisim(String asiisim){
		this.asiisim = asiisim;
	}

	public String getAsiisim(){
		return asiisim;
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

	@Override
 	public String toString(){
		return 
			"AsiModel{" + 
			"tf = '" + tf + '\'' + 
			",petresim = '" + petresim + '\'' + 
			",pettur = '" + pettur + '\'' + 
			",asitarih = '" + asitarih + '\'' + 
			",asiisim = '" + asiisim + '\'' + 
			",petisim = '" + petisim + '\'' + 
			",petcins = '" + petcins + '\'' + 
			"}";
		}
}
