package com.betacom.jpa.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MeDTO {
	private String id;
	private String role;
	private Boolean mailValidate;
	private Integer carelloSize;
}
