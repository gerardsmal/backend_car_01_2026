package com.betacom.jpa.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.betacom.jpa.dto.output.CarelloDTO;
import com.betacom.jpa.dto.output.CarelloDetaglioDTO;
import com.betacom.jpa.models.RigaCarello;
import com.betacom.jpa.models.Utente;
import com.betacom.jpa.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class CarelloMapper {

	private final IUploadServices uplS; 
	
	public  CarelloDTO builCarelloDTO(Utente ut) {
		if (ut.getCarello() == null) return null;
		double totale = 0;
		for (RigaCarello r : ut.getCarello().getRigaCarello()) {
			totale = totale + (r.getVeicolo().getPrezzo() * r.getQuantita());
		}	
		
		
		return CarelloDTO.builder()
				.id(ut.getCarello().getId())
				.nome(ut.getNome())
				.cognome(ut.getCognome())
				.userName(ut.getUserName())
				.prezzoTotale(totale)
				.status(ut.getCarello().getStato().toString())
				.carelloDetaglios(buildDetaglioDTO(ut.getCarello().getRigaCarello()))
				.build();
	}
	

	public List<CarelloDetaglioDTO> buildDetaglioDTO(List<RigaCarello> rige){
		
		return rige.stream()
				.map (r -> CarelloDetaglioDTO.builder()
						.id(r.getId())
						.alimentazione(r.getVeicolo().getTipoAlimentazione().getNome())
						.categoria(r.getVeicolo().getCategorie().getNome())
						.colore(r.getVeicolo().getColore().getNome())
						.dataCreazione(r.getDataCreazione())
						.image(r.getVeicolo().getImage() == null ? null : uplS.buildUrl(r.getVeicolo().getImage()))
						.marca(r.getVeicolo().getMarca().getNome())
						.modello(r.getVeicolo().getModello())
						.prezzo(r.getVeicolo().getPrezzo())
						.quantita(r.getQuantita())
						.veicoloID(r.getVeicolo().getId())
						.tipoVeicolo(r.getVeicolo().getTipoVeicolo().getNome())
						.build()
						).toList();
	}
}
