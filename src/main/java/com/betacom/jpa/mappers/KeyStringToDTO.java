package com.betacom.jpa.mappers;

import java.util.List;

import com.betacom.jpa.dto.output.KeyStringDTO;
import com.betacom.jpa.models.BaseKeyString;

public class KeyStringToDTO {

	public static List<KeyStringDTO> keyStringToDTO (List<? extends BaseKeyString> lI){
		return lI.stream()
				.map(i ->  keyStringToDTO(i))
				.toList();
		
	}
	
	public static KeyStringDTO keyStringToDTO(BaseKeyString i) {
	    return KeyStringDTO.builder()
	            .id(i.getId())
	            .nome(i.getNome())
	            .build();
	}

}
