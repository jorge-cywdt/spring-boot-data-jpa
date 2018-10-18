package com.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.producto;

public interface productoDao_Interfaz extends CrudRepository<producto, Long> {
	
	/*
	@Query("select p from producto p where p.nombre like %?1%")
	public List<producto> findByNombre(String term);
	*/
	
	@Query(value = "select * from productos where nombre like %?1%",
		   nativeQuery = true)
	public List<producto> findByNombre(String term); // El parametro es el termino ó texto, es decir el nombre del producto

}
