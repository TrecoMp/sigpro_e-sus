package com.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.model.Profissional;
import com.sistema.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	public void adicionarProfissional(Profissional profissional) {
		profissionalRepository.save(profissional);
	}
	
	public List<Profissional> listarProfissional(){
		return profissionalRepository.findAll();
	}
	
	public void removerProfissional(Long id) {
		profissionalRepository.deleteById(id);
	}
	
	public List<Profissional> buscarPorUbs(String equipe){
		return profissionalRepository.findByEquipe(equipe);
	}
	
	public Profissional buscaPorCns(Long cns) {
		return profissionalRepository.findByCns(cns);
	}
}
