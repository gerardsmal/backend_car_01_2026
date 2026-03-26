package com.betacom.jpa.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="biciclette")
public class Bici {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name="numero_marce",
			nullable = false)
	private Integer numeroMarce;
	
	@Column (nullable = false)
	private Boolean pieghevole;

	@ManyToOne
	@JoinColumn (name="id_tipo_sospenzione")
	private Sospenzione sospenzione;

	@OneToOne (cascade = CascadeType.REMOVE)
	@JoinColumn(
			name="veicolo_id",
			referencedColumnName = "id"
			)
	private Veicolo veicolo;

}
