package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.KeyStringReq;
import com.betacom.jpa.dto.output.KeyStringDTO;

public interface ITipoAlimentazioneServices {
	void create(KeyStringReq req) throws Exception;
	
	List<KeyStringDTO> list(String pattern);

}
