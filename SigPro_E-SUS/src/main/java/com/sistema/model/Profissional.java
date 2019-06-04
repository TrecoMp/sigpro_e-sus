package com.sistema.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Profissional implements Serializable{
	
	@Id
	private Long cns;
	private String nome;
	private String equipe;
	private String cargo;
	private String ma;//micro area
	
	//teste para o regitro temporario
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public Long getCns() {
		return cns;
	}
	public void setCns(Long cns) {
		this.cns = cns;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEquipe() {
		return equipe;
	}
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	//atributos com relações no banco
	
	@OneToMany(mappedBy = "profissional")
	@JsonIgnore
	private List<Ficha> fichas;

	public List<Ficha> getFichas() {
		return fichas;
	}
	public void setFichas(List<Ficha> fichas) {
		this.fichas = fichas;
	}
	
	
}
