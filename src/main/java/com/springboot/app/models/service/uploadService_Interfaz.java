package com.springboot.app.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface uploadService_Interfaz {
	
	public String copy(MultipartFile file);
	
	public boolean delete(String filename);
	
	public Resource load(String filename);
	
}
