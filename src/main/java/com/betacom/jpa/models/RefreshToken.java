package com.betacom.jpa.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="refresh_token")
public class RefreshToken {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

    private String username;

    private Instant expiryDate;  //per avere time più preciso tempo assoluto (UTC)

    private boolean revoked;
}
