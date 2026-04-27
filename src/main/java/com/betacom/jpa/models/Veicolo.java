package com.betacom.jpa.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="veicolo")
public class Veicolo {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name="numero_ruote")
	private Integer numeroRuote;
	
	@Column (nullable = false,
			length = 100)
	private String  modello;
	
	@Column (
			nullable = false,
			name="anno_produzione"
			)
	private Integer annoProduzione;
	private Double prezzo;
	private String image;

	@Column (name="data_inserimento")
	private LocalDate dataInserimento;

	@ManyToOne
	@JoinColumn (name="id_tipo_veicolo")
	private TipoVeicolo tipoVeicolo;

	@ManyToOne
	@JoinColumn (name="id_categorie")
	private Categorie categorie;

	@ManyToOne
	@JoinColumn (name="id_tipo_alimentazione")
	private TipoAlimentazione tipoAlimentazione;

	@ManyToOne
	@JoinColumn (name="id_colore")
	private Colore colore;

	@ManyToOne
	@JoinColumn (name="id_marca")
	private Marca marca;
	
	@OneToOne(
			mappedBy = "veicolo",
			cascade =  CascadeType.REMOVE
			)
	private Macchina macchina;

	@OneToOne(
			mappedBy = "veicolo",
			cascade =  CascadeType.REMOVE
			)
	private Moto moto;

	@OneToOne(
			mappedBy = "veicolo",
			cascade =  CascadeType.REMOVE
			)
	private Bici bici;
	
	@OneToMany(
			mappedBy = "veicolo",
			fetch = FetchType.EAGER
			)
	private List<RigaCarello> rigaCarello;
	
	

}
