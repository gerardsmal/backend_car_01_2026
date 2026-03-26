package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.VeicoloReq;
import com.betacom.jpa.dto.output.VeicoloDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.mappers.VeicoloMapper;
import com.betacom.jpa.models.Categorie;
import com.betacom.jpa.models.Colore;
import com.betacom.jpa.models.Marca;
import com.betacom.jpa.models.TipoAlimentazione;
import com.betacom.jpa.models.TipoVeicolo;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.ICategorieRepository;
import com.betacom.jpa.repositories.IColoreRepository;
import com.betacom.jpa.repositories.IMarcaRepository;
import com.betacom.jpa.repositories.ITipoAlimentazioneRepository;
import com.betacom.jpa.repositories.ITipoVeicoloRepository;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IVeicoliServices;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class VeicoloImpl  implements IVeicoliServices{
	
	private final IVeicoloRepository veR;
	private final IMessageServices msgS;
	private final ITipoVeicoloRepository tipoVR;
	private final ICategorieRepository  catR;
	private final ITipoAlimentazioneRepository tipoAR;
	private final IColoreRepository colR;
	private final IMarcaRepository  marR;
	private final VeicoloMapper  veiM;
	
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public Integer create(VeicoloReq req) throws Exception {
		log.debug("create {}" , req);
		Veicolo v = new Veicolo();
		TipoVeicolo tV = tipoVR.findById(req.getTipoVeicolo())
			.orElseThrow(() -> new AcademyException(msgS.get("tipo_veicolo_invalid") + ":" + req.getTipoVeicolo()));
		v.setTipoVeicolo(tV);
		Categorie cat = catR.findById(req.getCategorie())
				.orElseThrow(() -> new AcademyException(msgS.get("cat_invalid") + ":" + req.getCategorie()));
		v.setCategorie(cat);
		TipoAlimentazione tipoA = tipoAR.findById(req.getTipoAlimentazione())
				.orElseThrow(() -> new AcademyException(msgS.get("alim_invalid") + ":" + req.getTipoAlimentazione()));
		v.setTipoAlimentazione(tipoA);
		Colore col = colR.findById(req.getColore())
				.orElseThrow(() -> new AcademyException(msgS.get("colore_invalid")+ ":" + req.getColore()));
		v.setColore(col);
		Marca marca = marR.findById(req.getMarca())
				.orElseThrow(() -> new AcademyException(msgS.get("marca_invalid") +  ":" + req.getMarca()));
		v.setMarca(marca);
		if (req.getModello() == null)
			throw new AcademyException(msgS.get("model_invalid"));
		v.setModello(req.getModello());	
		
		if (req.getAnnoProduzione() == null)
			throw new AcademyException(msgS.get("anno_invalid"));
		
		if (req.getAnnoProduzione() < LocalDate.now().getYear()- 10 || req.getAnnoProduzione() > LocalDate.now().getYear())
			throw new AcademyException(msgS.get("anno_invalid")+  ":" + req.getAnnoProduzione());
		v.setAnnoProduzione(req.getAnnoProduzione());
		
		if (req.getNumeroRuote() == null)
			throw new AcademyException(msgS.get("ruote_invalid"));
		v.setNumeroRuote(req.getNumeroRuote());
		
		
		if (req.getPrezzo() == null)
			throw new AcademyException(msgS.get("prezzo_invalid"));
		v.setDataInserimento(LocalDate.now());
		return veR.save(v).getId();
		
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(VeicoloReq req, Veicolo v) throws Exception {
		log.debug("Update via macchina {}", req);
		if (req.getCategorie() != null) {
			Categorie cat = catR.findById(req.getCategorie())
					.orElseThrow(() -> new AcademyException(msgS.get("cat_invalid") + ":" + req.getCategorie()));
			v.setCategorie(cat);
		}
		
		if (req.getTipoAlimentazione() != null) {
			TipoAlimentazione tipoA = tipoAR.findById(req.getTipoAlimentazione())
					.orElseThrow(() -> new AcademyException(msgS.get("alim_invalid") + ":" + req.getTipoAlimentazione()));
			v.setTipoAlimentazione(tipoA);			
		}
		
		if (req.getColore() != null) {
			Colore col = colR.findById(req.getColore())
					.orElseThrow(() -> new AcademyException(msgS.get("colore_invalid")+ ":" + req.getColore()));
			v.setColore(col);			
		}
		if (req.getMarca() != null) {
			Marca marca = marR.findById(req.getMarca())
					.orElseThrow(() -> new AcademyException(msgS.get("marca_invalid") +  ":" + req.getMarca()));
			v.setMarca(marca);
		}
		
		if (req.getModello() != null)
			v.setModello(req.getModello());	
		
		if (req.getAnnoProduzione() != null) {
			if (req.getAnnoProduzione() < LocalDate.now().getYear()- 10 || req.getAnnoProduzione() > LocalDate.now().getYear())
				throw new AcademyException(msgS.get("anno_invalid")+  ":" + req.getAnnoProduzione());
			v.setAnnoProduzione(req.getAnnoProduzione());		
		}
		
		
		if (req.getNumeroRuote() != null)
			v.setNumeroRuote(req.getNumeroRuote());
		
		if (req.getPrezzo() != null)
			v.setPrezzo(req.getPrezzo());

		veR.save(v);
	}

	@Override
	public List<VeicoloDTO> find(Integer id, Integer tipo, String categoria, String alimentazione, Integer colore,
			Integer marca, String targa, Integer porte) {
		log.debug("find {}/{}/{}/{}/{}/{}/{}/{}", id, tipo, categoria, alimentazione, colore, marca, targa, porte);
		
		List<Veicolo> lV = veR.searchByFilter(id, tipo, categoria, alimentazione, colore, marca, targa, porte);
		
		log.debug("Size:" + lV.size());
		
		
		return veiM.builVeicoloDTO(lV);
	}

}
