package com.example.veterineruygulamasi.Models;

public class PetModel{
	private boolean tf;
	private String petid;
	private String petresim;
	private String pettur;
	private String petisim;
	private String petcins;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPetid(String petid){
		this.petid = petid;
	}

	public String getPetid(){
		return petid;
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
			"PetModel{" + 
			"tf = '" + tf + '\'' + 
			",petid = '" + petid + '\'' + 
			",petresim = '" + petresim + '\'' + 
			",pettur = '" + pettur + '\'' + 
			",petisim = '" + petisim + '\'' + 
			",petcins = '" + petcins + '\'' + 
			"}";
		}
}
