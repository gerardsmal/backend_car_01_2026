package com.betacom.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.Macchina;

public interface IMacchinaRepository extends JpaRepository<Macchina, Integer>{
	Boolean existsByTarga(String targa);
}
