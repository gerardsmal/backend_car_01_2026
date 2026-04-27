package com.betacom.jpa.security.implemantations;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.betacom.jpa.security.interfaces.JwtServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class JwtImpl implements JwtServices{
	
	private final SecretKey key;    // la secret key é messa dentro il file di properties. Si fa generare
									// Linux/Mac : openssl rand -base64 64
									// Windows : [Convert]::ToBase64String((1..64 | ForEach-Object {Get-Random -Maximum 256}))
	
	public JwtImpl(@Value("${app.jwt.secret}") String secret) {
		log.debug("JwtImpl : {}", secret);
	    try {
	        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	        log.debug("Key generate from contructor: {}", this.key );
	    } catch (Exception e) {
	        throw new IllegalStateException(
	            "Configurazione JWT non valida: controlla app.jwt.secret. Deve essere Base64 valido e abbastanza lungo.",
	            e
	        );
	    }
	}

	@Override
	public String generateAccessToken(Authentication authentication) {
		log.debug("generateAccessToken ");
		Instant now = Instant.now();

        List<String> roles = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        
        log.debug("roles {}", roles);
        
        String t = Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(900)))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
        
        log.debug("Token : {}",t);
        
        return t;
	}

}
