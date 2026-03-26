package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.BiciReq;

public interface IBiciServices {

	void create(BiciReq req) throws Exception;
	void update(BiciReq req) throws Exception;
	void delete(Integer id) throws Exception;
}
