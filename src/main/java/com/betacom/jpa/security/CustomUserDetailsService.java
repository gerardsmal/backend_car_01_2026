package com.betacom.jpa.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.betacom.jpa.models.Utente;
import com.betacom.jpa.repositories.IUtenteRepository;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * questa class controlla se l'utente esiste
 * é chiamata da authenticationManager.authenticate
 * il controllo del password si fa tramite DaoAuthenticationProvider di spring security
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final IUtenteRepository utR;
	private final IMessageServices msgS;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("loadUserByUsername: {}", username);
		
		Utente ut = utR.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(msgS.get("login_invalid")));
		
        return User.builder()
                .username(ut.getUserName())
                .password(ut.getPwd()) 
                .roles(ut.getRole().toString())       // "ADMIN" o "USER"
                .build();

	}

}
