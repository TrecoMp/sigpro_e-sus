package com.sistema.model;

import java.io.Serializable;
import java.util.Date;

public class MsgAdd implements Serializable{
	private long id_prof;
	private long id_registro;
	private String data;
	private String pessoa; //apenas para um uso em RegistroControl.salvarRegistro
	
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	public long getId_prof() {
		return id_prof;
	}
	public void setId_prof(long id_prof) {
		this.id_prof = id_prof;
	}
	public long getId_registro() {
		return id_registro;
	}
	public void setId_registro(long id_registro) {
		this.id_registro = id_registro;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
