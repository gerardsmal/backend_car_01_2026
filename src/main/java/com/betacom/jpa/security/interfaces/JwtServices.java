package com.betacom.jpa.security.interfaces;

import org.springframework.security.core.Authentication;

public interface JwtServices {
	String generateAccessToken(Authentication authentication);
}
