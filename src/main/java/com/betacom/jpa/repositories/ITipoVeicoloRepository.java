package com.betacom.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.TipoVeicolo;

public interface ITipoVeicoloRepository extends JpaRepository<TipoVeicolo, Integer>{
	Boolean existsByNome(String nome);

}
