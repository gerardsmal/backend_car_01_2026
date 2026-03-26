package com.betacom.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.jpa.models.Veicolo;

public interface IVeicoloRepository extends JpaRepository<Veicolo, Integer>{
	@Query (name ="veicoli.selectByFilter")
	List<Veicolo> searchByFilter(
			@Param("id") Integer id,
			@Param("tipo") Integer tipo,
			@Param("categoria") String categoria,
			@Param("alimentazione") String alimentazione,
			@Param("colore") Integer colore,
			@Param("marca") Integer marca,
			@Param("targa") String targa,
			@Param("porte") Integer porte
			);

}
