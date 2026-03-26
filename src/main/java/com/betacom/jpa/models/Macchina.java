package com.betacom.jpa.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="macchina")
public class Macchina {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name="numero_porte",
			 nullable = false)
	private Integer numeroPorte;
	
	@Column (nullable = false,
			unique = true)
	private String targa;
	
	@Column (nullable = false)
	private Integer cc;
	
	@OneToOne (cascade = CascadeType.REMOVE)
	@JoinColumn(
			name="veicolo_id",
			referencedColumnName = "id"
			)
	private Veicolo veicolo;
	

}
