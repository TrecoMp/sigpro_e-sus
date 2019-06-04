package com.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sistema.model.Ficha;

public interface FichaRepository extends JpaRepository<Ficha, Long>{
	/* BUSCA NATIVA - NÃO ESTA SENDO UTILIZADA - TA AQUI SO POR ESTUDO
	@Query(value="select * from ficha where id_profissional =	?1", nativeQuery = true)
	List<Ficha> findById_profissional(long id_profissional);
	*/
}