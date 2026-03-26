package com.betacom.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.jpa.models.Categorie;

public interface ICategorieRepository extends JpaRepository<Categorie, String>{

	@Query (name ="categoria.findByFilter")
	List<Categorie> findByFilter (@Param("id") String filter);
}
