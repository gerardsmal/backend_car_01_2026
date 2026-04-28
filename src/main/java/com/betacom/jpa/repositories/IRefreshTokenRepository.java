package com.betacom.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.RefreshToken;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	 
	Optional<RefreshToken> findByToken(String token);
}
