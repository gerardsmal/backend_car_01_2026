package com.betacom.jpa.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Query (
		value = """
		select v from Veicolo v 
			join v.tipoVeicolo tipoV
			join v.categorie cat 
			join v.tipoAlimentazione alim 
			join v.colore col 
			join v.marca marca 
			left join v.macchina macchina 
			left join v.moto moto 
			left join v.bici bici 
			where (:id is null or v.id = :id) 
				and (:tipo is null or tipoV.id = :tipo) 
				and (:categoria is null or cat.id = :categoria) 
				and (:alimentazione is null or alim.id = :alimentazione) 
				and (:colore is null or col.id = :colore) 
				and (:marca is null or marca.id = :marca) 
				and (:porte is null or macchina.numeroPorte = :porte) 
				and (:targa is null or macchina.targa = :targa or moto.targa = :targa)
			""",
		   countQuery = """
			select count(distinct v.id) from Veicolo v 
				join v.tipoVeicolo tipoV
				join v.categorie cat 
				join v.tipoAlimentazione alim 
				join v.colore col 
				join v.marca marca 
				left join v.macchina macchina 
				left join v.moto moto 
				left join v.bici bici 
				where (:id is null or v.id = :id) 
					and (:tipo is null or tipoV.id = :tipo) 
					and (:categoria is null or cat.id = :categoria) 
					and (:alimentazione is null or alim.id = :alimentazione) 
					and (:colore is null or col.id = :colore) 
					and (:marca is null or marca.id = :marca) 
					and (:porte is null or macchina.numeroPorte = :porte) 
					and (:targa is null or macchina.targa = :targa or moto.targa = :targa)
				
				
				 """)
	Page<Veicolo> searchByFilterPaging(
			@Param("id") Integer id,
			@Param("tipo") Integer tipo,
			@Param("categoria") String categoria,
			@Param("alimentazione") String alimentazione,
			@Param("colore") Integer colore,
			@Param("marca") Integer marca,
			@Param("targa") String targa,
			@Param("porte") Integer porte,
			Pageable pageable
			);

}
