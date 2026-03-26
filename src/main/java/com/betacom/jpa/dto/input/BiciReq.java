package com.betacom.jpa.dto.input;

import com.betacom.jpa.models.Sospenzione;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BiciReq extends VeicoloReq{

	private Integer numeroMarce;
	private Boolean pieghevole;
	private Integer sospenzione;


}
