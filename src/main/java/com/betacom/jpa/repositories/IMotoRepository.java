package com.betacom.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.Moto;

public interface IMotoRepository extends JpaRepository<Moto, Integer>{
	Boolean existsByTarga(String targa);

}
