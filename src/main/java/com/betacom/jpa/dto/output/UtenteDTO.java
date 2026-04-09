package com.betacom.jpa.dto.output;

import enums.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UtenteDTO {

	private String userName;
	private String pwd;
	private String  role; 
	private String nome;
	private String cognome;
	private Boolean sesso;
	private String email;
	private String telefono;
	private String via;
	private String comune;
	private String cap;
	private Boolean isValidate;
	
}
