package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.KeyStringReq;
import com.betacom.jpa.dto.output.KeyStringDTO;

public interface ICategorieServices {
	void create(KeyStringReq req) throws Exception;
	
	List<KeyStringDTO> list(String categoria);
}
