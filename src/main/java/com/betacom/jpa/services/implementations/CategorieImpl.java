package com.betacom.jpa.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.KeyStringReq;
import com.betacom.jpa.dto.output.KeyStringDTO;
import com.betacom.jpa.models.Categorie;
import com.betacom.jpa.repositories.ICategorieRepository;
import com.betacom.jpa.services.interfaces.ICategorieServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import static com.betacom.jpa.mappers.KeyStringToDTO.keyStringToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategorieImpl implements ICategorieServices{
	private final ICategorieRepository catR;
	private final IMessageServices  msgS;
	
	
	@Override
	public void create(KeyStringReq req) throws Exception {
		log.debug("create {}", req);
		Categorie cat = new Categorie();
		cat.setId(req.getKey());
		cat.setNome(req.getValue());
		
		catR.save(cat);
		
	}

	@Override
	public List<KeyStringDTO> list(String categoria) {
		log.debug("List: {}" , categoria);
		List<Categorie> lC = catR.findByFilter(categoria);
		
		return keyStringToDTO(lC);
	}

}
