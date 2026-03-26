package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.KeyIntegerReq;
import com.betacom.jpa.dto.output.KeyIntegerDTO;

public interface IColoreServices {
	void create(KeyIntegerReq req) throws Exception;
	
	List<KeyIntegerDTO> list();
}
