package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.TipoVeicoloReq;
import com.betacom.jpa.dto.output.TipoVeicoloDTO;

public interface ITipoVeicoloServices {

	void create(TipoVeicoloReq req) throws Exception;
	List<TipoVeicoloDTO> list();
}
