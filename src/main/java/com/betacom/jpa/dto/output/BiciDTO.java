package com.betacom.jpa.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BiciDTO {
	private Integer id;
	private Integer numeroMarce;
	private Boolean pieghevole;
	private KeyIntegerDTO sospenzione;

}
