package com.betacom.jpa.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MotoDTO {
	private Integer id;
	private String targa;
	private Integer cc;

}
