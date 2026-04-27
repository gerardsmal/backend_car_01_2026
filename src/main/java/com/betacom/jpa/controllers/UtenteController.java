package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.ChangePwdReq;
import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.dto.input.UtenteReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/utente")

public class UtenteController {

	private final IUtenteServices utS;
	private final IMessageServices msgS;
	

	
	@PostMapping("/public/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  UtenteReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	
	@PutMapping("/user/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true)  UtenteReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@DeleteMapping("/admin/delete/{id}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true)  String id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.delete(id);
			r.setMsg(msgS.get("rest_deleted"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@GetMapping("/admin/list")
	public ResponseEntity<Object> list(
			@RequestParam (required = false)  String userName,
			@RequestParam (required = false)  String nome,
			@RequestParam (required = false)  String cognome,
			@RequestParam (required = false)  String role
			){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= utS.list(userName, nome, cognome, role);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}
	
	@GetMapping("/user/findByUserName")
	public ResponseEntity<Object> findById (@RequestParam (required = true)  String id){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= utS.getByUserName(id);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}
	
	@GetMapping("/public/sendResetPassword")
	public ResponseEntity<Resp> sendResetPssword (@RequestParam (required = true)  String id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.sendResetPssword(id);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}




	@GetMapping("/publi/emailValidate")
	public ResponseEntity<Resp> emailValidate (@RequestParam (required = true)  String id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.emailValidate(id);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}



	@PutMapping("/public/resetPassword")
	public ResponseEntity<Resp> resetPssword(@RequestBody(required = true)  ChangePwdReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.resetPssword(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	
	@PutMapping("/user/changePwd")
	public ResponseEntity<Resp> changePwd(@RequestBody(required = true)  ChangePwdReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			utS.changePwd(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

}
