package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.VeicoloReq;
import com.betacom.jpa.dto.output.VeicoloDTO;
import com.betacom.jpa.dto.page.PageResponseDTO;
import com.betacom.jpa.models.Veicolo;

public interface IVeicoliServices {
	Integer create(VeicoloReq req) throws Exception;
	
	void update(VeicoloReq req, Veicolo veicolo) throws Exception;
	
	
	List<VeicoloDTO> find(Integer id, Integer tipo, String categoria, 
			String alimentazione, Integer colore, Integer marca, String targa, Integer porte);
	
	PageResponseDTO<VeicoloDTO> findByPage(Integer page, Integer size, String sortBy, String direction,Integer id, Integer tipo, String categoria, 
			String alimentazione, Integer colore, Integer marca, String targa, Integer porte);
	
	VeicoloDTO getById(Integer id) throws Exception;
}
