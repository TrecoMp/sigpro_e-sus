package com.sistema.model;

import java.io.Serializable;
import java.util.Date;

public class Relatorio implements Serializable{
	
	private String equipe;
	private String datainicio;
	private String datafim;
	
	public String getEquipe() {
		return equipe;
	}
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
	public String getDatainicio() {
		return datainicio;
	}
	public void setDatainicio(String datainicio) {
		this.datainicio = datainicio;
	}
	public String getDatafim() {
		return datafim;
	}
	public void setDatafim(String datafim) {
		this.datafim = datafim;
	}
	
	
}
