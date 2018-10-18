package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ventas")
public class venta implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	private String observacion;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
		
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	/*************** Relación de tablas (CLIENTES, VENTAS) ***************/
		
	@ManyToOne(fetch=FetchType.LAZY) // Muchas ventas pueden ser realizadas por un cliente 
	@JoinColumn(name="cliente_id") // Nombre del campo que estara en la tabla VENTAS	
	private cliente cliente;	

	/*************** Relación de tablas (VENTAS, DETALLE_VENTAS) ***************/
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL) // Una venta puede tener muchos detalles de ventas
	@JoinColumn(name="venta_id") // Nombre del campo que estara en la tabla DETALLE_VENTAS
	private List<detalleVenta> listDetalleVenta; 		
	
	public void addDetalleVenta(detalleVenta obj) {
		listDetalleVenta.add(obj);
	}		
	
	/*********************************/
	
	public venta() {
		listDetalleVenta = new ArrayList<detalleVenta>();		
	}	
	public Long getId() {
		return id;
	}	
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}				
	public cliente getCliente() {
		return cliente;
	}
	public void setCliente(cliente cliente) {
		this.cliente = cliente;
	}
	public List<detalleVenta> getListDetalleVenta() {
		return listDetalleVenta;
	}
	public void setListDetalleVenta(List<detalleVenta> listDetalleVenta) {
		this.listDetalleVenta = listDetalleVenta;
	}

	/*********************************/
	
	public Double getTotal() {
		Double total = 0.0;		
		int size = listDetalleVenta.size();
		
		for (int i=0; i<size; i++) {
			total += listDetalleVenta.get(i).calcularImporte();
		}
		return total;
	}
	
}
