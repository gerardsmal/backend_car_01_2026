package com.betacom.jpa.mappers;

import com.betacom.jpa.dto.output.BiciDTO;
import com.betacom.jpa.models.Bici;
import static com.betacom.jpa.mappers.KeyIntegerToDTO.keyIntegerToDTO;

public class BiciMapper {

	
	public static BiciDTO builBiciDTO(Bici v) {
		return BiciDTO.builder()
				.id(v.getId())
				.pieghevole(v.getPieghevole())
				.sospenzione(keyIntegerToDTO(v.getSospenzione()))
				.numeroMarce(v.getNumeroMarce())
				.build();								
	}
	
}
