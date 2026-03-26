package com.betacom.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.jpa.models.TipoAlimentazione;

public interface ITipoAlimentazioneRepository extends JpaRepository<TipoAlimentazione, String>{

	@Query (name ="alim.findByFilter")
	List<TipoAlimentazione> findByFilter (@Param("id") String filter);

}
