package com.betacom.jpa.controllers;

import com.betacom.jpa.security.CustomUserDetailsService;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
>>>>>>> security
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.response.Resp;
<<<<<<< HEAD
=======
import com.betacom.jpa.security.interfaces.IRefreshTokenServices;
import com.betacom.jpa.security.interfaces.JwtServices;
>>>>>>> security
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/auth")
public class AuthController {

	private final CustomUserDetailsService customUserDetailsService;
	private final IUtenteServices utS;
	private final IMessageServices msgS;
<<<<<<< HEAD

	@PostMapping("/login")
	public ResponseEntity<Object> me (@RequestBody(required = true)  LoginReq req){
=======
	private final AuthenticationManager authenticationManager;
    private final JwtServices jwtService;
    private final IRefreshTokenServices refreshTokenService;

	
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq request,  HttpServletResponse response) {
    	HttpStatus status = HttpStatus.OK;
    	Object r = new Object();
    	try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUserName(),
                    request.getPwd()
             ));
            
            String accessToken = jwtService.generateAccessToken(authentication);
            String refreshToken = refreshTokenService.createRefreshToken(authentication.getName());  // create refresh token in db

            ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                    .httpOnly(true)
                    .secure(false) // true in produzione con HTTPS
                    .sameSite("Lax")
                    .path("/rest/auth")
                    .maxAge(Duration.ofDays(7))
                    .build();
            
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString()); // invio il cookie HTTP Only
            
            r = LoginDTO.builder()
            		.accessToken(accessToken)
            		.tokenType("Bearer")
            		.build();
            
  
        } catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
     		r = new Resp();
    		((Resp)r).setMsg(e.getMessage());     
        }
        return ResponseEntity.status(status).body(r);
        
    }
  
    /*
     * in questo caso il parametro  ricevo come cookie il token
     */
    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(
    		@CookieValue(name = "refresh_token", required = false) String refreshToken) {
    	HttpStatus status = HttpStatus.OK;
    	Object r = new Object();
    	try {
            if (refreshToken == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing refresh token");
            }

            String username = refreshTokenService.validateAndGetUsername(refreshToken);
            String newAccessToken = jwtService.generateAccessTokenFromUsername(username);

            r = LoginDTO.builder()
            		.accessToken(newAccessToken)
            		.tokenType("Bearer")
            		.build();
            
    		
    	} catch (Exception e) {
    	     status = HttpStatus.UNAUTHORIZED;
      		r = new Resp();
     		((Resp)r).setMsg(e.getMessage());
		}
    	 return ResponseEntity.status(status).body(r);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response) {
    	
        if (refreshToken != null) {
            refreshTokenService.revokeToken(refreshToken);
        }

        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(false) // true in produzione HTTPS
            .sameSite("Lax")
            .path("/rest/auth")
            .maxAge(0)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.noContent().build();
    }
    
    
	@GetMapping("/me")
	public ResponseEntity<Object> me (Authentication authentication){
>>>>>>> security
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			String username = authentication.getName();
			r= utS.me(username);
			
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
