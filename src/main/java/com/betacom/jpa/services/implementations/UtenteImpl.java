package com.betacom.jpa.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.input.LoginReq;
import com.betacom.jpa.dto.input.MailReq;
import com.betacom.jpa.dto.input.UtenteReq;
import com.betacom.jpa.dto.input.ChangePwdReq;
import com.betacom.jpa.dto.output.MeDTO;
import com.betacom.jpa.dto.output.UtenteDTO;
import com.betacom.jpa.enums.Roles;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Utente;
import com.betacom.jpa.repositories.IUtenteRepository;
import com.betacom.jpa.services.interfaces.IMailServices;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUtenteServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UtenteImpl implements IUtenteServices{

	@Value("${mail.validation}")
	private String validationURL;

	@Value("${mail.resetPassword}")
	private String resetPasswordURL;
	
	private final IUtenteRepository utR;
	private final IMessageServices msgS;
	private final PasswordEncoder encoder;
	private final IMailServices  mailS;
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void create(UtenteReq req) throws Exception {
		log.debug("create {}", req);	
		if (utR.existsById(req.getUserName()))
			throw new AcademyException(msgS.get("user_exist"));
		
		
		Utente acc = new Utente();
		acc.setUserName(req.getUserName());
		acc.setPwd(req.getPwd());  // encode password

		acc.setNome(req.getNome());
		acc.setCognome(req.getCognome());
		acc.setEmail(req.getEmail());
		acc.setComune(req.getCommune());
		acc.setVia(req.getVia());
		acc.setCap(req.getCap());
		acc.setTelefono(req.getTelefono());
		acc.setPwd(encoder.encode(req.getPwd()));  
		acc.setRole(Roles.valueOf(req.getRole()));
		acc.setDataCreazione(LocalDate.now());
		
		acc.setSesso(req.getSesso());
		acc.setValidate(false);
		utR.save(acc);
		sendMailValidation(acc);
		
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void update(UtenteReq req) throws Exception {
		log.debug("update {}", req);
		boolean isMailChanged = false;
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));
		if (req.getPwd() != null)
			ut.setPwd(encoder.encode(req.getPwd()));
		if (req.getEmail() != null) {
			ut.setEmail(req.getEmail());
			ut.setValidate(false);
			isMailChanged = true;
		}
		if (req.getRole() != null)
			ut.setRole(Roles.valueOf(req.getRole()));
		if (req.getNome() != null)
			ut.setNome(req.getNome());
		if (req.getCognome() != null)
			ut.setCognome(req.getCognome());
		if (req.getCommune() != null)
			ut.setComune(req.getCommune());
		if (req.getVia() != null)
			ut.setVia(req.getVia());
		if (req.getCap() != null)
			ut.setCap(req.getCap());
		if (req.getTelefono() != null)	
			ut.setTelefono(req.getTelefono());
		if (req.getSesso() != null)	
			ut.setSesso(req.getSesso());
		
		utR.save(ut);
		if (isMailChanged)
			sendMailValidation(ut);
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(String userName) throws Exception {
		log.debug("delete {}", userName);
		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));

		utR.delete(ut);
	}

	@Override
	public List<UtenteDTO> list(String userName, String nome, String cognome, String role) {
		log.debug("list {}/{}/{}/{}", userName, nome, cognome, role);
		Roles ro = null;
		if (role != null)  ro = Roles.valueOf(role.trim().toUpperCase());
		
		List<Utente> lU = utR.selectByFilter(userName, nome, cognome, ro);
		
		return lU.stream()
				.map((u -> UtenteDTO.builder()
						.userName(u.getUserName())
						.nome(u.getNome())
						.cognome(u.getCognome())
						.via(u.getVia())
						.comune(u.getComune())
						.cap(u.getCap())
						.email(u.getEmail())
						.role(u.getRole().toString())
						.sesso(u.getSesso())
						.telefono(u.getTelefono())
						.isValidate(u.getValidate())
						.build())
						).toList();
						
	}

	@Override
	public UtenteDTO getByUserName(String userName) throws Exception {
		log.debug("getByUserName {}", userName);
		Utente u = utR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));
		
		return  UtenteDTO.builder()
				.userName(u.getUserName())
				.nome(u.getNome())
				.cognome(u.getCognome())
				.via(u.getVia())
				.comune(u.getComune())
				.cap(u.getCap())
				.email(u.getEmail())
				.role(u.getRole().toString())
				.sesso(u.getSesso())
				.telefono(u.getTelefono())
				.isValidate(u.getValidate())
				.build();
	}

	@Override
	public MeDTO login(LoginReq req) throws Exception {
		log.debug("login {}", req);
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException(msgS.get("login_invalid")));
		if (!encoder.matches(req.getPwd(), ut.getPwd()))
			throw new AcademyException(msgS.get("login_invalid"));
		
		return MeDTO.builder()
				.id(ut.getUserName())
				.role(ut.getRole().toString())
				.mailValidate(ut.getValidate())
				.build();
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void changePwd(ChangePwdReq req) throws Exception {
		log.debug("changePwd {}", req);
		
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));

		if (!encoder.matches(req.getOldPwd(), ut.getPwd()))
			throw new Exception(msgS.get("login_invalid"));
		
		Optional.ofNullable(req.getNewPwd())
			.ifPresentOrElse(pwd -> {
				ut.setPwd(encoder.encode(req.getNewPwd()));
			}, () -> { 
				throw new RuntimeException(msgS.get("user_no_newpwd"));
			});
		
		utR.save(ut);
	}

	@Override
	public void sendValidation(String userName) throws Exception {
		log.debug("sendValidation {}", userName);

		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));
		sendMailValidation(ut);

	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void emailValidate(String userName) throws Exception {
		log.debug("emailValidate {}", userName);
		
		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));	
		ut.setValidate(true);
		utR.save(ut);
		
	}


	@Override
	public void sendResetPssword(String userName) throws Exception {
		log.debug("sendResetPssword {}", userName);
		
		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));	
		StringBuilder body = new StringBuilder();
		body.append("<h2>Vendita Veicoli</h2><br><br>");
		body.append("Buongiorno ");
		body.append(ut.getNome());
		body.append("<br><br>");
		body.append("<br>Per inizializzare la tua password va sull'URL");
		body.append("<br><a>"+ resetPasswordURL  + ut.getUserName()+ "</a><br>");
		body.append("<br><br>Il team Vendita Veicoli <br><br>");

		sendMail(ut, "Validazione email", body.toString());
	}


	@Override
	public void resetPssword(ChangePwdReq req) throws Exception {
		log.debug("resetPssword {}", req);
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException(msgS.get("user_ntfnd")));

		Optional.ofNullable(req.getNewPwd())
			.ifPresentOrElse(pwd -> {
				ut.setPwd(encoder.encode(req.getNewPwd()));
			}, () -> { 
				throw new RuntimeException(msgS.get("user_no_newpwd"));
			});
		
		utR.save(ut);

		
	}

	
	private void sendMailValidation(Utente acc) throws Exception{
		StringBuilder body = new StringBuilder();
		body.append("<h2>Vendita Veicoli</h2><br><br>");
		body.append("Buongiorno ");
		body.append(acc.getNome());
		body.append("<br><br>");
		body.append("<br>Per validare tuo mail va sull'URL");
		body.append("<br><a>"+ validationURL  + acc.getUserName()+ "</a><br>");
		body.append("<br><br>Il team Vendita Veicoli <br><br>");

		sendMail(acc, "Validazione email", body.toString());
	}

	private void sendMail(Utente account, String oggetto, String body) throws Exception{
		
		mailS.sendMail(MailReq.builder()
				.to(account.getEmail())
				.oggetto(oggetto)
				.body(body)
				.build()
				);
		

	}



	
}
