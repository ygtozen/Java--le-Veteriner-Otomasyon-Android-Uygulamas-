package com.example.veterineradmin.Models;

public class KullanicilarModel{
	private boolean tf;
	private String telefon;
	private String kadi;
	private String id;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setKadi(String kadi){
		this.kadi = kadi;
	}

	public String getKadi(){
		return kadi;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"KullanicilarModel{" + 
			"tf = '" + tf + '\'' + 
			",telefon = '" + telefon + '\'' + 
			",kadi = '" + kadi + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
