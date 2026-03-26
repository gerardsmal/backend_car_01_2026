package com.betacom.jpa.services.implementations;

import org.springframework.stereotype.Service;

import com.betacom.jpa.models.Messaggi;
import com.betacom.jpa.repositories.IMessageRepository;
import com.betacom.jpa.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessagesImpl  implements IMessageServices{

	private final IMessageRepository msgR;
	
	@Override
	public String get(String code) {
		log.debug("get {}", code);
		Messaggi msg = msgR.findById(code)
				.orElse( new  Messaggi(code, code));
		
		return msg.getMsg();
	}

}
