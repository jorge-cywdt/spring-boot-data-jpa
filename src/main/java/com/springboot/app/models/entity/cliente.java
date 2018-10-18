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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="clientes") // Esta notación se puede omitir cuando la tabla se llama exactamente igual que la clase // El estandar en BD el nombre de tablas terminan en plural y el estandar de clases es singular 				
public class cliente implements Serializable { // Buena práctica para trabajar con Spring Data JPA
		
	private static final long serialVersionUID = 1L; // Como implementamos Serializable tenemos que agregar este atributo estático

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty // @NotEmpty valida que el campo String tenga contenido
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email // Notación para confirmar que sea email
	private String email;
	
	@NotNull // @NotNull valida que el campo no sea nulo, si validamos un campo String con @NotNull el objeto siempre va a existir, ya que va a ser un String pero vacio, por eso es importante validar los String con @NotEmpty
	@Column(name="create_at") // Por estándar en la tabla el nombre se separa con guión bajo
	@Temporal(TemporalType.DATE) // Notación para la fecha
	@DateTimeFormat(pattern="yyyy-MM-dd") // Notación para el formato de la fehca
	private Date createAt; // java.util
	
	private String foto; // Puede ser un String sin contenido '' ó null

	/*************** Relación de tablas (CLIENTES, VENTAS) ***************/
		
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL) // Un cliente puede realizar muchas ventas
	private List<venta> listVenta;
	
	public void addVenta(venta obj) {
		listVenta.add(obj);
	}	
			
	/*********************************/
	
	public cliente() {
		listVenta = new ArrayList<venta>(); // Inicializamos el List
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public List<venta> getListVenta() {
		return listVenta;
	}
	public void setListVenta(List<venta> listVenta) {
		this.listVenta = listVenta;
	}					
	
	/*********************************/
		
	/*
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
		
	@PrePersist // @PrePersist significa antes de que se guarde en la BD, con esta notacion, nos aseguramos que este metodo sea llamado justo antes de insertar los registros en la BD
	public void prePersist() {
		createAt = new Date();
	}
	*/
	
	/* 
	mappedBy, mapeado por el atributo idCliente que actuara como llave foranea y que se encuentra en la clase venta
	LAZY, carga perezosa, solo realiza la consulta cuando se le llame, getListVenta() 
	cascade, el cliente se elimina también se eliminara de forma automatica todas sus facturas (ventas)
	*/
	
}
