package com.betacom.jpa.mappers;

import com.betacom.jpa.dto.output.VeicoloDTO;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;

import static com.betacom.jpa.mappers.TipoVeicoloMapper.tipoVeicoloToDTO;
import static com.betacom.jpa.mappers.KeyStringToDTO.keyStringToDTO;
import static com.betacom.jpa.mappers.KeyIntegerToDTO.keyIntegerToDTO;
import static com.betacom.jpa.mappers.MacchinaMapper.builMacchinaDTO;
import static com.betacom.jpa.mappers.MotoMapper.builMotoDTO;
import static com.betacom.jpa.mappers.BiciMapper.builBiciDTO;

import java.util.List;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VeicoloMapper {
	private final IUploadServices uplS; 
	
	
	public  VeicoloDTO builVeicoloDTO(Veicolo v) {
		return VeicoloDTO.builder()
				.id(v.getId())
				.numeroRuote(v.getNumeroRuote())
				.modello(v.getModello())
				.annoProduzione(v.getAnnoProduzione())
				.prezzo(v.getPrezzo())
				.image(uplS.buildUrl(v.getImage()))
				.dataInserimento(v.getDataInserimento())
				.tipoVeicolo(tipoVeicoloToDTO(v.getTipoVeicolo()))
				.categoria(keyStringToDTO(v.getCategorie()))
				.tipoAlimentazione(keyStringToDTO(v.getTipoAlimentazione()))
				.colore(keyIntegerToDTO(v.getColore()))
				.marca(keyIntegerToDTO(v.getMarca()))
				.macchina(v.getMacchina() == null ? null :builMacchinaDTO(v.getMacchina()))
				.moto(v.getMoto() == null ? null : builMotoDTO(v.getMoto()))
				.bici(v.getBici() == null ? null : builBiciDTO(v.getBici()))
				.build();								
	}
	
	public  List<VeicoloDTO> builVeicoloDTO(List<Veicolo> lV){
		return lV.stream()
				.map(v -> builVeicoloDTO(v))
				.toList();
				
				
	}
}
