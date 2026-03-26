package com.betacom.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseKeyString {
	@Id
	@Column (length = 50)
	private String id;

	@Column (nullable = false, length = 50)
	private String nome;

}
