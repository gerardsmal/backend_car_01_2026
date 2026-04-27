package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/auth")
public class AuthController {

	private final IUtenteServices utS;
	private final IMessageServices msgS;

	@PostMapping("/login")
	public ResponseEntity<Object> me (@RequestBody(required = true)  LoginReq req){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= utS.login(req);
		} catch (Exception e) {
			r = new Resp();
			((Resp)r).setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}

	@GetMapping("sendValidation")
	public ResponseEntity<Resp> sendValidation (@RequestParam (required = true)  String id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.sendValidation(id);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}

}
