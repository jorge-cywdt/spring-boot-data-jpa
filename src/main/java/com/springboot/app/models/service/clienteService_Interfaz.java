package com.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.app.models.entity.cliente;

public interface clienteService_Interfaz {
	
	public List<cliente> findAll();
	
	public cliente findOne(Long id);

	public void save(cliente obj);
	
	public void delete(cliente obj);
	
	public Page<cliente> findAll(Pageable pageable);	
	
}
