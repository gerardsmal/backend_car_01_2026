package com.betacom.jpa.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MacchinaDTO {
	private Integer id;
	private Integer numeroPorte;
	private String targa;
	private Integer cc;

}
