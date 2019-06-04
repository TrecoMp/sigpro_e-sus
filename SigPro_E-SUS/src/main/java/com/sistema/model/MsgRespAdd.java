package com.sistema.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MsgRespAdd implements Serializable{
	private String nome;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date data;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public MsgRespAdd(String nome, Date data) {
		super();
		this.nome = nome;
		this.data = data;
	}
	
}
