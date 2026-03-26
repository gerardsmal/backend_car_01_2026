package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.BiciReq;
import com.betacom.jpa.dto.input.MacchinaReq;
import com.betacom.jpa.dto.input.MotoReq;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Bici;
import com.betacom.jpa.models.Macchina;
import com.betacom.jpa.models.Moto;
import com.betacom.jpa.models.Sospenzione;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.IBiciRepository;
import com.betacom.jpa.repositories.IMacchinaRepository;
import com.betacom.jpa.repositories.IMotoRepository;
import com.betacom.jpa.repositories.ISospenzioneRepository;
import com.betacom.jpa.repositories.ITipoVeicoloRepository;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.IBiciServices;
import com.betacom.jpa.services.interfaces.IMacchinaServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IMotoServices;
import com.betacom.jpa.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BiciImpl implements IBiciServices{
	private final IMessageServices msgS;
	private final IBiciRepository biciR;
	private final IVeicoliServices  veS;
	private final IVeicoloRepository  veR;
	private final ISospenzioneRepository sosR;
	
	private static String REGULAR_TARGA = "^[A-HJ-NPR-Z]{2}\\d{5}$";
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void create(BiciReq req) throws Exception {
		log.debug("create {}", req);
		
		Veicolo veicolo = veR.findById(veS.create(req))
				.orElseThrow(() -> new AcademyException(msgS.get("fatal")));
		
		Bici bi = new Bici();
		bi.setVeicolo(veicolo);
		Sospenzione spsp = sosR.findById(req.getSospenzione())
				.orElseThrow(() -> new AcademyException(msgS.get("sos_invalid")));
		bi.setSospenzione(spsp);
		if (req.getPieghevole() == null)
			req.setPieghevole(false);
		bi.setPieghevole(req.getPieghevole());
		if (req.getNumeroMarce() == null)
			throw new AcademyException(msgS.get("marce_invalid"));
		
		biciR.save(bi);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(BiciReq req) throws Exception {
		log.debug("update {}", req);
		
		Veicolo v = veR.findById(req.getId())
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + req.getId()));
				
		Bici bi = v.getBici();
				
		if (req.getSospenzione() != null) {
			Sospenzione spsp = sosR.findById(req.getSospenzione())
					.orElseThrow(() -> new AcademyException(msgS.get("sos_invalid")));
			bi.setSospenzione(spsp);			
		}
		
		
		if (req.getPieghevole() != null)
			bi.setPieghevole(req.getPieghevole());
	
		if (req.getNumeroMarce() != null)
			bi.setNumeroMarce(req.getNumeroMarce());
	
		
		biciR.save(bi);	
	
		veS.update(req,v);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Bici bi = biciR.findById(id)
				.orElseThrow(() -> new AcademyException(msgS.get("mac_ntfnd") + ":" + id));
		biciR.delete(bi);
	}

}
