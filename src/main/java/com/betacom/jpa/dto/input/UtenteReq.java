package com.betacom.jpa.dto.input;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UtenteReq {
	private String nome;
	private String cognome;
	private String email;	
	private Boolean sesso; // true mas. false fem)
	private String telefono;
	private String via;
	private String commune;
	private String cap;
	private String userName;
	private String pwd;	
	private String role;


}
