package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.KeyStringReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.ICategorieServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/categoria")
public class CategoriaController {

	private final ICategorieServices catS;
	private final IMessageServices   msgS;
	
	
	@PostMapping("/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  KeyStringReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			catS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@GetMapping("/list")
	public ResponseEntity<Object> list(@RequestParam (required = false)  String categoria){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= catS.list(categoria);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}

}
