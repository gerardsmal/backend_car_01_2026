package com.betacom.jpa.models;

import java.time.LocalDate;
import java.util.List;

import com.betacom.jpa.enums.StatoCarello;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table (name="carello")
public class Carello {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="data_creazione")
	private LocalDate dataCreazione;

	
	@Column (name="stato_carello")
	private StatoCarello stato;
	
	@OneToOne
	@JoinColumn(
			name="utente_userName",
			referencedColumnName = "userName"
			)
	private Utente utente;
	
	@OneToMany(
			mappedBy = "carello",
			fetch = FetchType.EAGER,
			cascade = CascadeType.REMOVE
			)
	private List<RigaCarello> rigaCarello;
}
