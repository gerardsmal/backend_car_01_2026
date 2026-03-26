package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.MotoReq;

public interface IMotoServices {

	void create(MotoReq req) throws Exception;
	void update(MotoReq req) throws Exception;
	void delete(Integer id) throws Exception;
}
