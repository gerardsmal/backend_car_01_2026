package com.betacom.jpa.security.interfaces;

public interface IRefreshTokenServices {
	
	String createRefreshToken(String username);
	String validateAndGetUsername(String token);
	void revokeToken(String token);
}
