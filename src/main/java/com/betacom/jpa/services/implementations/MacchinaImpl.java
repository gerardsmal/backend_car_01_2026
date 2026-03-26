package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.MacchinaReq;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Macchina;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.IMacchinaRepository;
import com.betacom.jpa.repositories.ITipoVeicoloRepository;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.IMacchinaServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MacchinaImpl implements IMacchinaServices{
	private final IMessageServices msgS;
	private final IMacchinaRepository macR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	
	private static String REGULAR_TARGA = "^[A-HJ-NPR-Z]{2}\\d{3}[A-HJ-NPR-Z]{2}$";
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void create(MacchinaReq req) throws Exception {
		log.debug("create {}", req);
		
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException(msgS.get("fatal")));
		
		Macchina mac = new Macchina();
		mac.setVeicolo(veicolo);
		
		if (!req.getTarga().matches(REGULAR_TARGA))
			throw new AcademyException(msgS.get("targa_invalid") + ":" + req.getTarga());
		if (macR.existsByTarga(req.getTarga()))
			throw new AcademyException(msgS.get("targa_exist") + ":" + req.getTarga());
		mac.setTarga(req.getTarga());
		if (req.getNumeroPorte() == null) 
			throw new AcademyException(msgS.get("porte_invalid"));
		mac.setNumeroPorte(req.getNumeroPorte());
		if (req.getCc() == null)
			throw new AcademyException(msgS.get("cc_invalid"));
		mac.setCc(req.getCc());
		
		macR.save(mac);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(MacchinaReq req) throws Exception {
		log.debug("update {}", req);
		
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + req.getId()));
		Macchina mac = v.getMacchina();
				
		if (req.getTarga() != null) {
			if (!req.getTarga().matches(REGULAR_TARGA))
				throw new AcademyException(msgS.get("targa_invalid") + ":" + req.getTarga());
			if (macR.existsByTarga(req.getTarga()))
				throw new AcademyException(msgS.get("targa_exist") + ":" + req.getTarga());
			mac.setTarga(req.getTarga());			
		}
		if (req.getNumeroPorte() != null) 
			mac.setNumeroPorte(req.getNumeroPorte());

		if (req.getCc() != null)
			mac.setCc(req.getCc());
	
		macR.save(mac);	
	
		veS.update(req, v);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Macchina mac = macR.findById(id)
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + id));
		
		macR.delete(mac);
	}

}
