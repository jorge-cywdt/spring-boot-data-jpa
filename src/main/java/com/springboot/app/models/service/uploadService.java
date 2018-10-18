package com.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class uploadService implements uploadService_Interfaz {

	private final Logger log = LoggerFactory.getLogger(getClass());	

	@Override
	public String copy(MultipartFile filename) {		
		
		String uniqueFilename = UUID.randomUUID().toString() + "_" + filename.getOriginalFilename(); // Nombre único por cada foto																											
		Path rootAbsolutPath = getAbsolutPath(uniqueFilename); // Ruta absoluta
		log.info("rootAbsolutPath: " + rootAbsolutPath);

		try {
			Files.copy(filename.getInputStream(), rootAbsolutPath); // Guarda la foto en la ruta absoluta		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uniqueFilename;
		
	}

	@Override
	public boolean delete(String filename) {
		
		Path rootAbsolutPath = getAbsolutPath(filename);
		File archivo = rootAbsolutPath.toFile();
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) { // Metodo delete retorna un booleano
				return true;
			}
		}
		return false;
		
	}
	
	/************************************************************/
	
	@Override
	public Resource load(String filename) {
		
		Path fotoAbsolutPath = getAbsolutPath(filename);
		log.info("fotoAbsolutPath: " + fotoAbsolutPath);
		
		Resource recurso = null;
		try {
			recurso = new UrlResource(fotoAbsolutPath.toUri());
			if (!recurso.exists() && !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen: " + fotoAbsolutPath.toString());
			}
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}	
		return recurso;
		
	}
	
	/************************************************************/
	
	public Path getAbsolutPath(String filename) {		
		return Paths.get("uploads").resolve(filename).toAbsolutePath();		
	}

}
