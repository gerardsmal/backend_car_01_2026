package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("rest/veicolo")
public class VeicoloController {

	private final IVeicoliServices veiS;	
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(
			@RequestParam (required = false)  Integer id,
			@RequestParam (required = false)  Integer tipo,
			@RequestParam (required = false)  String categoria,
			@RequestParam (required = false)  String alimentazione,
			@RequestParam (required = false)  Integer colore,
			@RequestParam (required = false)  Integer marca,
			@RequestParam (required = false)  String targa,
			@RequestParam (required = false)  Integer porte
			
			){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= veiS.find(id, tipo, categoria, alimentazione, colore, marca, targa, porte);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}
}

