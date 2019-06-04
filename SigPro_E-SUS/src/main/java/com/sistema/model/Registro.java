package com.sistema.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Registro implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Timestamp data;
	private String tipo; //se é devolução ou recebimento
	private String pessoa; //que esta entregando ou recebendo, no caso o profisional de saude
	private String digitador;
	
	@OneToMany(mappedBy = "registro",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true,
			fetch= FetchType.EAGER)
	@JsonIgnore
	private List<Ficha> fichas;

	public List<Ficha> getFichas() {
		return fichas;
	}
	public void setFichas(List<Ficha> fichas) {
		this.fichas = fichas;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	public String getDigitador() {
		return digitador;
	}
	public void setDigitador(String digitador) {
		this.digitador = digitador;
	}
}
