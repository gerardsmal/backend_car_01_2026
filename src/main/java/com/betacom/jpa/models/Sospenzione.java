package com.betacom.jpa.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name="tipo_sospenzione")
public class Sospenzione extends BaseKeyInteger{

	@OneToMany(
			mappedBy = "sospenzione",
			fetch = FetchType.EAGER
			)
	private List<Bici> bicis;

}
