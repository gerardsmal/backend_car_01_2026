package com.betacom.jpa.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeicoloReq {

	private Integer id;
	private Integer numeroRuote;
	private String  modello;
	private Integer annoProduzione;
	private Integer tipoVeicolo;
	private String categorie;
	private String tipoAlimentazione;
	private Integer colore;
	private Integer marca;
	private Double prezzo;

}
