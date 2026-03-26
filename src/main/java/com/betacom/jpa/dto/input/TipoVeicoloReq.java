package com.betacom.jpa.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TipoVeicoloReq {

	private Integer id;
	private String nome;
	private String pattern;

}
