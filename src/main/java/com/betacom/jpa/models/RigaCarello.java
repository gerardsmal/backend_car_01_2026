package com.betacom.jpa.models;

import java.time.LocalDate;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="detaglio_carello")
public class RigaCarello {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="data_creazione")
	private LocalDate dataCreazione;
	
	@NotNull
	@Min(1)
	@Column (nullable = false)
	private Integer quantita;
	
	@ManyToOne
	@JoinColumn (name="id_carello")
	private Carello carello;

	@ManyToOne
	@JoinColumn (name="id_veicolo")
	private Veicolo veicolo;
	
}
