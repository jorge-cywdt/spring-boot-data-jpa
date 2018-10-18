package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.productoDao_Interfaz;
import com.springboot.app.models.entity.producto;

@Service
public class productoService implements productoService_Interfaz {	

	@Autowired
	private productoDao_Interfaz productoDao;
	
	/******************************/
	
	@Override
	public List<producto> findByNombre(String term) {		
		return productoDao.findByNombre(term);
	}
	
}
