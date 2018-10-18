package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.producto;

public interface productoService_Interfaz {

	public List<producto> findByNombre(String term);
	
}
