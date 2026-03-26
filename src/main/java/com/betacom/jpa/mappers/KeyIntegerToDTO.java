package com.betacom.jpa.mappers;

import java.util.List;

import com.betacom.jpa.dto.output.KeyIntegerDTO;
import com.betacom.jpa.models.BaseKeyInteger;

public class KeyIntegerToDTO {

	public static List<KeyIntegerDTO> keyIntegerToDTO (List<? extends BaseKeyInteger> lI){
		return lI.stream()
				.map(i ->  keyIntegerToDTO(i))
				.toList();
		
	}

	
	public static KeyIntegerDTO keyIntegerToDTO(BaseKeyInteger i) {
	    return KeyIntegerDTO.builder()
	            .id(i.getId())
	            .nome(i.getNome())
	            .build();
	}
}
