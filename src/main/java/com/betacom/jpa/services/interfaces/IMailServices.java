package com.betacom.jpa.services.interfaces;

import com.betacom.jpa.dto.input.MailReq;

public interface IMailServices {
	void sendMail(MailReq req) throws Exception;
}
