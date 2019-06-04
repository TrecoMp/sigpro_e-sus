package com.sistema.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import com.sistema.model.Pessoa;

@Entity
public class Role implements GrantedAuthority{

	@Id
	private String papel;
	
	@ManyToMany(mappedBy = "roles")
	private List<Pessoa> pessoas;

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.papel;
	}

	
}
