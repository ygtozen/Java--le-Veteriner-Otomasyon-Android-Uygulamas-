package com.example.veterineruygulamasi.Models;

public class KampanyaModel{
	private String resim;
	private boolean tf;
	private String text;
	private String baslik;

	public void setResim(String resim){
		this.resim = resim;
	}

	public String getResim(){
		return resim;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setBaslik(String baslik){
		this.baslik = baslik;
	}

	public String getBaslik(){
		return baslik;
	}

	@Override
 	public String toString(){
		return 
			"KampanyaModel{" + 
			"resim = '" + resim + '\'' + 
			",tf = '" + tf + '\'' + 
			",text = '" + text + '\'' + 
			",baslik = '" + baslik + '\'' + 
			"}";
		}
}
