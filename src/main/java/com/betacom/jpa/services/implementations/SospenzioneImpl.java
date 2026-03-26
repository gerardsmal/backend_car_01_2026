package com.betacom.jpa.services.implementations;

import static com.betacom.jpa.mappers.KeyIntegerToDTO.keyIntegerToDTO;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.KeyIntegerReq;
import com.betacom.jpa.dto.output.KeyIntegerDTO;
import com.betacom.jpa.models.Colore;
import com.betacom.jpa.models.Sospenzione;
import com.betacom.jpa.repositories.IColoreRepository;
import com.betacom.jpa.repositories.ISospenzioneRepository;
import com.betacom.jpa.services.interfaces.IColoreServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.ISospenzioneServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SospenzioneImpl implements ISospenzioneServices{
	private final ISospenzioneRepository sosR;
	private final IMessageServices  msgS;
	
	
	@Override
	public void create(KeyIntegerReq req) throws Exception {
		log.debug("create {}", req);
		Sospenzione sos = new Sospenzione();
		sos.setNome(req.getValue());
		
		sosR.save(sos);
		
	}

	@Override
	public List<KeyIntegerDTO> list() {
		log.debug("List");
		List<Sospenzione> lS = sosR.findAll();
		
		return keyIntegerToDTO(lS);
	}

}
