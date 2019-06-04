package com.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.model.Ficha;
import com.sistema.repository.FichaRepository;

@Service
public class FichaService {

	@Autowired
	private FichaRepository fichaRepository;
	
	public void adicionarFicha(Ficha ficha) {
		fichaRepository.save(ficha);
	}
	
	public List<Ficha> listaFichas(){
		return fichaRepository.findAll();
	}
	
	public void removerFicha(Long id) {
		fichaRepository.deleteById(id);
	}
}
