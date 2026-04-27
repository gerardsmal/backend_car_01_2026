package com.betacom.jpa.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/upload")
public class UploadController {

	private final IUploadServices uplS;
	private final IMessageServices msgS;
	
	@PostMapping(value = "/admin/image", consumes = "multipart/form-data")
	public ResponseEntity<Resp> uploadImage(
			@RequestParam MultipartFile file,
			@RequestParam Integer id) {
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			 /*
			   Test del content type:
						PNG	image/png
						JPG	image/jpeg
						GIF	image/gif
			  */
			 if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
				 r.setMsg(msgS.get("upload_invalid"));
				 return ResponseEntity.badRequest().body(r);	            
			 }
			 
			 r.setMsg(uplS.saveImage(file, id));
			 
			 return ResponseEntity.status(HttpStatus.CREATED).body(r);
			 
		 } catch (Exception e) {
			 r.setMsg(e.getMessage());
			 return ResponseEntity.internalServerError().body(r);
		 }
	 }
	
	@GetMapping("/admin/getUrl")
	public ResponseEntity<Resp> getUrl(@RequestParam (required = true) String filename) {
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		
		r.setMsg(uplS.buildUrl(filename));
		return ResponseEntity.status(status).body(r);
	}
}
