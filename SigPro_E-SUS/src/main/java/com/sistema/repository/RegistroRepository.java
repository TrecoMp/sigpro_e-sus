package com.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.model.Registro;


public interface RegistroRepository extends JpaRepository<Registro, Long>{
	@Transactional
	void removeByTipo(String tipo);
}