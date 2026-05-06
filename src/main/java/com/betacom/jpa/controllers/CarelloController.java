package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.CarelloReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.ICarelloServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("rest/carello")
public class CarelloController {
	
	private final ICarelloServices carS;
	private final IMessageServices msgS;

	
	@PostMapping("/user/addRiga")
	public ResponseEntity<Resp> addRiga(Authentication authentication,@RequestBody(required = true)  CarelloReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			String username = authentication.getName();
			req.setUtenteID(username);
			carS.addRiga(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@PatchMapping("/user/updateRiga")
	public ResponseEntity<Resp> updateRiga(Authentication authentication,@RequestBody(required = true)  CarelloReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			String username = authentication.getName();
			req.setUtenteID(username);
			carS.updateRiga(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@DeleteMapping("/user/deleteRiga/{id}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true)  Integer id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			carS.deleteRiga(id);
			r.setMsg(msgS.get("rest_deleted"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@GetMapping("/user/list")
	public ResponseEntity<Object> list(Authentication authentication){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			String username = authentication.getName();
			r=carS.getCarello(username);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}
}
