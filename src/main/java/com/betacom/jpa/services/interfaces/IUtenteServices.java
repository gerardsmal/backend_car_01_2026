package com.betacom.jpa.services.interfaces;

import java.util.List;

import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.dto.input.UtenteReq;
import com.betacom.jpa.dto.input.ChangePwdReq;
import com.betacom.jpa.dto.output.MeDTO;
import com.betacom.jpa.dto.output.UtenteDTO;


public interface IUtenteServices {
	
	void create(UtenteReq req) throws Exception;
	void update(UtenteReq req) throws Exception;
	void delete(String userName) throws Exception;
	void changePwd(ChangePwdReq req) throws Exception;
	void sendValidation(String userName) throws Exception;
	void emailValidate(String userName) throws Exception;
	void sendResetPssword(String userName) throws Exception;
	void resetPssword(ChangePwdReq req) throws Exception;
	
	MeDTO me(String id) throws Exception;
	List<UtenteDTO> list(String userName, String nome, String cognome, String role);
	UtenteDTO getByUserName(String userName) throws Exception;
}