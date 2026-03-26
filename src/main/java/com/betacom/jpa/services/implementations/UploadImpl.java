package com.betacom.jpa.services.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Veicolo;
import com.betacom.jpa.repositories.IVeicoloRepository;
import com.betacom.jpa.services.interfaces.IMessageServices;
import com.betacom.jpa.services.interfaces.IUploadServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadImpl implements IUploadServices{

	private final Path uploadPath;
	private final  IMessageServices msgS;
	private final IVeicoloRepository veiR;
	
	
	public UploadImpl(@Value("${app.upload.dir:uploads}") String uploadDir,  // valore per default della value
			IMessageServices msgS, IVeicoloRepository veiR ) {
	        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize(); // transform relative path in absolute  path
	        this.msgS = msgS;
	        this.veiR = veiR;
	        init();
	    }	
	
	private void init() {
		try {
			if (Files.notExists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(msgS.get("upload_create"));
		}
	}
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public String saveImage(MultipartFile file, Integer id) throws Exception {
		log.debug("saveImage {}", id);
		
		Assert.isTrue(!file.isEmpty(),() -> msgS.get("upload_empty")); // control file loaded
		
        String original = file.getOriginalFilename();
        String extension = "";
        String originalName = original.trim().replaceAll("\\s+", "_"); // normalize file name
 
        log.debug("originalName: {}" , originalName);
        
        extension = Optional.ofNullable(originalName)         // search extension file 
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf(".")))
                .orElse("");

        // Build unique name
        String uniqueName =  originalName.substring(0, originalName.lastIndexOf(".")) + "-" +  UUID.randomUUID().toString() + extension;

        Path destinationFile = uploadPath.resolve(uniqueName);
        
        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            Veicolo v = veiR.findById(id)
            	.orElseThrow(() -> new AcademyException(msgS.get("veicolo_ntfnd")));	
            v.setImage(uniqueName);
            
        } catch (IOException e) {
            throw new RuntimeException(msgS.get("upload_save_error"));
        }
    
        return uniqueName;
	}

	@Override
	public void removeImage(String filename) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String buildUrl(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()  // recupera la parte iniziale dell URL // localhost:8080/
                .path("/images/")    // il prefisse sarebbe image
                .path(filename)                 // il nome del file
                .toUriString(); 	
	
	}

}
