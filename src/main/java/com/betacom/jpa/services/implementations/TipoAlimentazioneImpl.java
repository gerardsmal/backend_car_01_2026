package com.betacom.jpa.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.KeyStringReq;
import com.betacom.jpa.dto.output.KeyStringDTO;
import com.betacom.jpa.models.Categorie;
import com.betacom.jpa.models.TipoAlimentazione;
import com.betacom.jpa.repositories.ICategorieRepository;
import com.betacom.jpa.repositories.ITipoAlimentazioneRepository;
import com.betacom.jpa.services.interfaces.ICategorieServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.ITipoAlimentazioneServices;

import static com.betacom.jpa.mappers.KeyStringToDTO.keyStringToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoAlimentazioneImpl implements ITipoAlimentazioneServices{
	private final ITipoAlimentazioneRepository tipoR;
	private final IMessageServices  msgS;
	
	
	@Override
	public void create(KeyStringReq req) throws Exception {
		log.debug("create {}", req);
		TipoAlimentazione cat = new TipoAlimentazione();
		cat.setId(req.getKey());
		cat.setNome(req.getValue());
		
		tipoR.save(cat);
		
	}

	@Override
	public List<KeyStringDTO> list(String pattern) {
		log.debug("List ; {}", pattern);
		List<TipoAlimentazione> lC = tipoR.findByFilter(pattern);
		
		return keyStringToDTO(lC);
	}

}
