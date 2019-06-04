package com.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.model.Pessoa;
import com.sistema.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public void adicionarPessoa(Pessoa pessoa) {
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));//criptografa a senha para salvar no banco
		pessoaRepository.save(pessoa);
	}
	
	public void atualizarPessoa(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}
	
	public List<Pessoa> listarPessoas(){
		return 	pessoaRepository.findAll();
	}
	
	public void removerPessoa(Long id) {
		pessoaRepository.deleteById(id);
	}
	
	public Pessoa buscarPorId(Long id) {
		return pessoaRepository.getOne(id);
	}
	
	public Pessoa buscaPorLogin(String login) {
		return pessoaRepository.findByLogin(login);
	}
	
}
