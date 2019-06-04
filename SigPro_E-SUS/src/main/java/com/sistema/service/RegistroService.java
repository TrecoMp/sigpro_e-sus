package com.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.model.Registro;
import com.sistema.repository.RegistroRepository;

@Service
public class RegistroService {
	
	@Autowired
	private RegistroRepository registroRepository;
	
	public long adicionarRegistro(Registro registro) {
		Registro r = registroRepository.save(registro);
		return r.getId();
	}
	
	public List<Registro> listarRegistros(){
		return registroRepository.findAll();
	}
	
	public void removerRegistro(Long id) {
		registroRepository.deleteById(id);
	}
	
	public Registro buscaRegistro(Long id) {
		return registroRepository.getOne(id);
	}
	
	public void removerRegistroTipo(String tipo) {
		registroRepository.removeByTipo(tipo);
	}
}
