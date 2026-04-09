package com.betacom.jpa.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.jpa.dto.input.TipoVeicoloReq;
import com.betacom.jpa.dto.output.TipoVeicoloDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.TipoVeicolo;
import com.betacom.jpa.repositories.ITipoVeicoloRepository;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.ITipoVeicoloServices;

import static com.betacom.jpa.mappers.TipoVeicoloMapper.tipoVeicoloToDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoVeicoloImpl implements ITipoVeicoloServices{
	private final ITipoVeicoloRepository tveiR;
	private final IMessageServices  msgR;
	
	
	@Override
	public void create(TipoVeicoloReq req) throws Exception {
		log.debug("create {}", req);
		if (tveiR.existsByNome(req.getNome()))
			throw new AcademyException(msgR.get("tipo_veicolo_exits"));
		TipoVeicolo tV = new TipoVeicolo();
		tV.setNome(req.getNome());
		tV.setPattern(req.getPattern());
		tveiR.save(tV);
	}

	@Override
	public List<TipoVeicoloDTO> list() {
		log.debug("List");
//		List<TipoVeicolo> lT = tveiR.findAll();
		List<TipoVeicolo> lT = tveiR.findByStatus(true);

		return tipoVeicoloToDTO(lT);	
	}

}
