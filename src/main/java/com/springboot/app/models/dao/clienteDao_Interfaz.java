package com.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.models.entity.cliente;

/*
public interface clienteinterfaz_Dao {
public interface clienteInterfaz_Dao extends CrudRepository<cliente, Long> { 
*/
public interface clienteDao_Interfaz extends PagingAndSortingRepository<cliente, Long> { // Se pone la Long por el tipo de dato que tiene el id
	
	/*
	public list<cliente> findall();
	
	public cliente findone(long id);
	
	public void save(cliente obj);	
	
	public void delete(cliente obj);
	*/
	
}
