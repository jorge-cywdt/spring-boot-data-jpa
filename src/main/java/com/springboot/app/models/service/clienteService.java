package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.clienteDao_Interfaz;
import com.springboot.app.models.entity.cliente;

@Service // Un @Service es un componente, los componentes se pueden inyectar con @Autowired
public class clienteService implements clienteService_Interfaz {

	@Autowired // Con esta notación busca un componente que implemente la interfaz clienteInterfaz_Dao, es decir clienteDao
	private clienteDao_Interfaz clienteDao;
	
	/******************************/
	
	@Override
	@Transactional(readOnly=true) // Solo lectura porque es una consulta, cuando es un insert se puede omitir el atributo readOnly
	public List<cliente> findAll() {		
		return (List<cliente>) clienteDao.findAll(); // return clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null); //	return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void save(cliente obj) {
		clienteDao.save(obj);
	}	

	@Override
	@Transactional
	public void delete(cliente obj) {
		clienteDao.delete(obj);
	}

	@Override
	public Page<cliente> findAll(Pageable pageable) { // Page es muy parecido a List	
		return clienteDao.findAll(pageable);
	}		
	
}
