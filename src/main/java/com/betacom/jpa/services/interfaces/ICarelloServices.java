package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.CarelloReq;
import com.betacom.jpa.dto.output.CarelloDTO;

public interface ICarelloServices {
	void addRiga(CarelloReq req) throws Exception;
	void updateRiga(CarelloReq req) throws Exception;
	void deleteRiga(Integer id) throws Exception;
	
	CarelloDTO getCarello(String userName) throws Exception;
}
