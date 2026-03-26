package com.betacom.jpa.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadServices {

	String saveImage(MultipartFile file, Integer id) throws Exception;
	
	void removeImage(String filename) throws Exception;
	String buildUrl(String filename);
	
}
