package com.betacom.jpa.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarelloReq {
	private Integer id;
	private String utenteID;
	private Integer veicoloID;
	private Integer quantita;
}
