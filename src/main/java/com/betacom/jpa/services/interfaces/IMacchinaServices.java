package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.MacchinaReq;

public interface IMacchinaServices {

	void create(MacchinaReq req) throws Exception;
	void update(MacchinaReq req) throws Exception;
	void delete(Integer id) throws Exception;
}
