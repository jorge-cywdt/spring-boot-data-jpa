package com.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.cliente;
import com.springboot.app.models.entity.producto;
import com.springboot.app.models.entity.venta;
import com.springboot.app.models.service.clienteService_Interfaz;
import com.springboot.app.models.service.productoService_Interfaz;

@Controller
@RequestMapping(value="/venta") // http://localhost:8080/venta
@SessionAttributes(value="venta")
public class ventaController {
	
	@Autowired
	private clienteService_Interfaz clienteService;
	
	@Autowired
	private productoService_Interfaz productoService;
	
	/******************************/
	
	@RequestMapping(value="/form/{clienteId}")  // http://localhost:8080/venta/form/1
	public String crear(@PathVariable(value="clienteId") Long clienteId, Model model, RedirectAttributes flash) {
		
		cliente objCli = new cliente();
		objCli = clienteService.findOne(clienteId);
		
		venta objVen = new venta(); 
		
		if (objCli == null) {
			flash.addFlashAttribute("error", "El ID del Cliente no existe en la BD");
			return "redirect:/listar";
		} else {
			objVen.setCliente(objCli); // Relación de tablas
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("venta", objVen);
			return "venta/form";
		}				
	}
	
	@RequestMapping(value="/cargarProductos/{term}", produces={"application/json"}) // Genera y produce una respuesta JSON // Esta url es llamada por medio de Jquery y Ajax
	public @ResponseBody List<producto> cargarListProducto(@PathVariable String term) { // @ResponseBody, transforma la salida en JSON y la muestra dentro del body de la respuesta
		return productoService.findByNombre(term);		
	}
	
}
