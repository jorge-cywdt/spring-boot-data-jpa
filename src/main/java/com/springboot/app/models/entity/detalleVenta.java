package com.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalle_ventas")
public class detalleVenta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer cantidad;
	
	/*************** Relación de tablas (PRODUCTOS, DETALLE_VENTAS) ***************/
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id") // Nombre del campo que estara en la tabla DETALLE_VENTAS
	private producto producto;		
	
	/*********************************/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}		
	
	/*********************************/
	
	public Double calcularImporte() {
		return cantidad * producto.getPrecio();
	}
	
}
