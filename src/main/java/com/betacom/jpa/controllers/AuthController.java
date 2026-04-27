package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.dto.output.LoginDTO;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.security.interfaces.JwtServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/auth")
public class AuthController {

	private final IUtenteServices utS;
	private final IMessageServices msgS;
	private final AuthenticationManager authenticationManager;
    private final JwtServices jwtService;
	
	
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq request) {
    	HttpStatus status = HttpStatus.OK;
    	Object r = new Object();
    	try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUserName(),
                    request.getPwd()
             ));
            
            String token = jwtService.generateAccessToken(authentication);
            r = LoginDTO.builder()
            		.accessToken(token)
            		.tokenType("Bearer")
            		.build();
            
  
        } catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
     		r = new Resp();
    		((Resp)r).setMsg(e.getMessage());     
        }
        return ResponseEntity.status(status).body(r);
        
    }
    
    
    
	@GetMapping("/me")
	public ResponseEntity<Object> me (@RequestParam(required = true)  String id){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= utS.me(id);
		} catch (Exception e) {
			r = new Resp();
			((Resp)r).setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
		
	}
	
	
	@GetMapping("/sendResetPassword")
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
}
