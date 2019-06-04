package com.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.model.Profissional;

public interface ProfissionalRepository  extends JpaRepository<Profissional, Long>{
	List<Profissional> findByEquipe(String equipe);
	Profissional findByCns(Long cns);
}
