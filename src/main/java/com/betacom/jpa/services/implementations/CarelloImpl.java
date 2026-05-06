package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.CarelloReq;
import com.betacom.jpa.dto.output.CarelloDTO;
import com.betacom.jpa.enums.StatoCarello;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.mappers.CarelloMapper;
import com.betacom.jpa.models.Carello;
import com.betacom.jpa.models.RigaCarello;
import com.betacom.jpa.models.Utente;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.ICarelloRepository;
import com.betacom.jpa.repositories.IRigaCarelloReposity;
import com.betacom.jpa.repositories.IUtenteRepository;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.ICarelloServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarelloImpl implements ICarelloServices{
	
	private final ICarelloRepository carelloR;
	private final IRigaCarelloReposity rigaR;
	private final IUtenteRepository  utenteR;
	private final IVeicoloRepository veicoloR;
	private final IMessageServices msgS;
	private final CarelloMapper mapperC;
	
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void addRiga(CarelloReq req) throws Exception {
		log.debug("addRiga {}" , req);
		Utente ut = utenteR.findById(req.getUtenteID())
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));
				
		Carello carello = (ut.getCarello() != null) ? ut.getCarello() : createCarello(ut);
		
		controlCarello(carello);

		RigaCarello riga = new RigaCarello();
		Veicolo veicolo = veicoloR.findById(req.getVeicoloID())
				.orElseThrow(() -> new AcademyException(msgS.get("veicolo_ntfnd")));
		riga.setCarello(carello);
		riga.setDataCreazione(LocalDate.now());
		riga.setVeicolo(veicolo);
		riga.setQuantita(req.getQuantita());
		rigaR.save(riga);
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void updateRiga(CarelloReq req) throws Exception {
		log.debug("updateRiga {}" , req);
		RigaCarello riga = rigaR.findById(req.getId())
				.orElseThrow(() -> new AcademyException(msgS.get("carello_riga_ntfnd")));
		
		controlCarello(riga.getCarello());
		
		if (req.getQuantita() != null) {
			Optional.ofNullable(req.getQuantita())
				.filter(q -> q > 0)
				.orElseThrow(() -> new AcademyException(msgS.get("carello_quantita_ko")));
			riga.setQuantita(req.getQuantita());
		}
		rigaR.save(riga);
	}
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void deleteRiga(Integer id) throws Exception {
		log.debug("deleteRiga {}" ,id);
		RigaCarello riga = rigaR.findById(id)
				.orElseThrow(() -> new AcademyException(msgS.get("carello_riga_ntfnd")));
		
		controlCarello(riga.getCarello());
		rigaR.delete(riga);
	}
	
	@Transactional (rollbackFor = AcademyException.class)
	public Carello createCarello(Utente ut) throws Exception{
		log.debug("createCarello {}", ut.getUserName());
		Carello car = new Carello();
		car.setDataCreazione(LocalDate.now());
		car.setUtente(ut);
		car.setStato(StatoCarello.valueOf("carello"));
		car.setId(carelloR.save(car).getId());
		return car;
	}
	
	@Transactional (rollbackFor = AcademyException.class)
	public void controlCarello(Carello carello) throws Exception{
		Optional.ofNullable(carello.getStato())
		.filter(stato -> stato == StatoCarello.valueOf("ordine"))
		.ifPresent(stato -> {
			throw new AcademyException(msgS.get("carello_not_available"));
		});

	}

	@Override
	public CarelloDTO getCarello(String userName) throws Exception {
		log.debug("getCarello {}", userName);
		
		Utente ut = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));
		
		
		
		return mapperC.builCarelloDTO(ut);
	}

}
