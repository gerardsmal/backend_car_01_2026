package com.betacom.jpa.dto.output;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarelloDetaglioDTO {
	private Integer id;
	private LocalDate dataCreazione;
	private Integer veicoloID;
	private String modello;
	private String image;
	private String marca;
	private String categoria;
	private String colore;
	private String alimentazione;
	private double prezzo;
	private Integer quantita;

}
