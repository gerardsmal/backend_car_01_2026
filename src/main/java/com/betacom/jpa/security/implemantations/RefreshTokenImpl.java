package com.betacom.jpa.security.implemantations;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.jpa.models.RefreshToken;
import com.betacom.jpa.repositories.IRefreshTokenRepository;
import com.betacom.jpa.security.interfaces.IRefreshTokenServices;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenImpl implements IRefreshTokenServices{

	private final IRefreshTokenRepository refreshR;
	private final IMessageServices msgS;
	
	@Value("${app.jwt.refresh-token-expiration-days}")
	private long refreshTokenExpirationDays;
	
	@Override
	public String createRefreshToken(String username) {
		log.debug("createRefreshToken {}", username);
		 String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(Instant.now().plus(Duration.ofDays(refreshTokenExpirationDays)));
        refreshToken.setRevoked(false);

        refreshR.save(refreshToken);
        return token;
	}

	@Override
	public String validateAndGetUsername(String token) {
		log.debug("validateAndGetUsername {}", token);
	    RefreshToken refreshToken = refreshR.findByToken(token)
	        .orElseThrow(() -> new RuntimeException(msgS.get("refresh_invalid")));

         if (refreshToken.isRevoked()) {
             throw new RuntimeException(msgS.get("refresh_revocated"));
         }

         if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
             throw new RuntimeException(msgS.get("refresh_scaduto"));
         }

         return refreshToken.getUsername();	}

	@Override
	public void revokeToken(String token) {
		log.debug("revokeToken {}", token);
		refreshR.findByToken(token).ifPresent(rt -> {
	            rt.setRevoked(true);
	            refreshR.save(rt);
		});
		
	}

}
