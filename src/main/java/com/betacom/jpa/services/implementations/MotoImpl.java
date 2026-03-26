package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.MacchinaReq;
import com.betacom.jpa.dto.input.MotoReq;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Macchina;
import com.betacom.jpa.models.Moto;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.IMacchinaRepository;
import com.betacom.jpa.repositories.IMotoRepository;
import com.betacom.jpa.repositories.ITipoVeicoloRepository;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.IMacchinaServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IMotoServices;
import com.betacom.jpa.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MotoImpl implements IMotoServices{
	private final IMessageServices msgS;
	private final IMotoRepository motR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	
	private static String REGULAR_TARGA = "^[A-HJ-NPR-Z]{2}\\d{5}$";
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void create(MotoReq req) throws Exception {
		log.debug("create {}", req);
		
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException(msgS.get("fatal")));
		
		Moto moto = new Moto();
		moto.setVeicolo(veicolo);
		
		
		if (!req.getTarga().matches(REGULAR_TARGA))
			throw new AcademyException(msgS.get("targa_invalid") + ":" + req.getTarga());
		if (motR.existsByTarga(req.getTarga()))
			throw new AcademyException(msgS.get("targa_exist") + ":" + req.getTarga());
		moto.setTarga(req.getTarga());
		if (req.getCc() == null)
			throw new AcademyException(msgS.get("cc_invalid"));
		moto.setCc(req.getCc());
		
		motR.save(moto);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(MotoReq req) throws Exception {
		log.debug("update {}", req);
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + req.getId()));
	
		Moto moto = v.getMoto();
		
		if (req.getTarga() != null) {
			if (!req.getTarga().matches(REGULAR_TARGA))
				throw new AcademyException(msgS.get("targa_invalid") + ":" + req.getTarga());
			if (motR.existsByTarga(req.getTarga()))
				throw new AcademyException(msgS.get("targa_exist") + ":" + req.getTarga());
			moto.setTarga(req.getTarga());			
		}

		if (req.getCc() != null)
			moto.setCc(req.getCc());
	
		motR.save(moto);	
	
		veS.update(req, v);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Moto moto = motR.findById(id)
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + id));
		
		motR.delete(moto);
	}

}
