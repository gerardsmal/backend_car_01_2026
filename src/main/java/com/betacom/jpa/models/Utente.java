package com.betacom.jpa.models;




import java.time.LocalDate;

import com.betacom.jpa.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="utente")
public class Utente {
	
	@Id
	private String userName;
	private String pwd;
	private Roles  role; 
	private String nome;
	private String cognome;
	private Boolean sesso;
	private String email;
	private String telefono;
	private String via;
	private String comune;
	private String cap;
	
	@Column (name="email_validation",
			nullable= false,
			columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean validate;
	
	
	@Column (name="data_creazione")
	private LocalDate dataCreazione;

	@OneToOne (
			mappedBy = "utente",
			cascade = CascadeType.REMOVE
			)
	private Carello carello;
	
		
}
