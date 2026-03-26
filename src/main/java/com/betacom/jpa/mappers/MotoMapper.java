package com.betacom.jpa.mappers;

import com.betacom.jpa.dto.output.MotoDTO;
import com.betacom.jpa.models.Moto;

public class MotoMapper {

	
	public static MotoDTO builMotoDTO(Moto v) {
		return MotoDTO.builder()
				.id(v.getId())
				.cc(v.getCc())
				.targa(v.getTarga())
				.build();								
	}
	
}
