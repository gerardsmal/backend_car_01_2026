package com.betacom.jpa.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@Builder
public class TipoVeicoloDTO {

	private Integer id;
	private String nome;
	private String pattern;

}
