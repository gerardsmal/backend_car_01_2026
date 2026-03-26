package com.betacom.jpa.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePwdReq {
	String userName;
	String oldPwd;
	String newPwd;
}
