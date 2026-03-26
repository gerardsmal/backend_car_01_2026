package com.betacom.jpa.mappers;

import com.betacom.jpa.dto.output.MacchinaDTO;
import com.betacom.jpa.models.Macchina;

public class MacchinaMapper {

	
	public static MacchinaDTO builMacchinaDTO(Macchina v) {
		return MacchinaDTO.builder()
				.id(v.getId())
				.cc(v.getCc())
				.targa(v.getTarga())
				.numeroPorte(v.getNumeroPorte())
				.build();								
	}
	
}
