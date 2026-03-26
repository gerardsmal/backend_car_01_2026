package com.betacom.jpa.services.implementations;

import static com.betacom.jpa.mappers.KeyIntegerToDTO.keyIntegerToDTO;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.KeyIntegerReq;
import com.betacom.jpa.dto.output.KeyIntegerDTO;
import com.betacom.jpa.models.Colore;
import com.betacom.jpa.models.Marca;
import com.betacom.jpa.repositories.IColoreRepository;
import com.betacom.jpa.repositories.IMarcaRepository;
import com.betacom.jpa.services.interfaces.IColoreServices;
import com.betacom.jpa.services.interfaces.IMarcaServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarcaImpl implements IMarcaServices{
	private final IMarcaRepository marR;
	private final IMessageServices  msgS;
	
	
	@Override
	public void create(KeyIntegerReq req) throws Exception {
		log.debug("create {}", req);
		Marca mar = new Marca();
		mar.setNome(req.getValue());
		
		marR.save(mar);
		
	}

	@Override
	public List<KeyIntegerDTO> list() {
		log.debug("List");
		List<Marca> lM = marR.findAll();
		
		return keyIntegerToDTO(lM);
	}

}
