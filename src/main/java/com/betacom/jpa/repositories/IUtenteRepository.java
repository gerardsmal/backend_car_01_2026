package com.betacom.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.jpa.models.Utente;

import enums.Roles;

public interface IUtenteRepository extends JpaRepository<Utente, String>{
	
	@Query(name="utente.selectByFilter")
	List<Utente> selectByFilter(
			@Param("userName") String userName,
			@Param("nome") String nome,
			@Param("cognome") String cognome,
			@Param("role") Roles role
			);
}
