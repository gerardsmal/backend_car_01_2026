package com.betacom.jpa.dto.output;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
public class VeicoloDTO {
	private Integer id;
	private Integer numeroRuote;
	private String  modello;
	private Integer annoProduzione;
	private Double prezzo;
	private String image;
	private LocalDate dataInserimento;
	private TipoVeicoloDTO tipoVeicolo;
	private KeyStringDTO categoria;
	private KeyStringDTO tipoAlimentazione;
	private KeyIntegerDTO colore;
	private KeyIntegerDTO marca;
	private MacchinaDTO macchina;
	private MotoDTO moto;
	private BiciDTO bici;
}
