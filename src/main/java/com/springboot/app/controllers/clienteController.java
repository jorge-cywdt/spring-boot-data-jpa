package com.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.cliente;
import com.springboot.app.models.service.clienteService_Interfaz;
import com.springboot.app.models.service.uploadService_Interfaz;
import com.springboot.app.pageable.pageRender; 

@Controller // Notación que marca la clase como un controlador
@SessionAttributes(value="cliente") // buscar(), crear(), ver()
public class clienteController {

	@Autowired // Con esta notación busca un componente que implemente la interfaz clienteServiceInterfaz, es decir clienteService	
	private clienteService_Interfaz clienteService;
	
	@Autowired // Con esta notación busca un componente que implemente la interfaz uploadServiceInterfaz, es decir uploadService	
	private uploadService_Interfaz uploadService;
	
			
	/******************************/
		
	@RequestMapping(value="/", method=RequestMethod.GET) // Si no se especifica el método por defecto es GET
	public String home(Model model) {
		model.addAttribute("titulo", "Home");
		return "home"; // templates
	}		
	
	@RequestMapping(value="/about")
	public String about(Model model) {
		model.addAttribute("titulo", "About");
		return "about";
	}
	
	/******************************/
	
	@RequestMapping(value="/listar")
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5); // Migrado a Spring 5 // Antes de Spring 5, Pageable pageRequest = new PageRequest(page, 5);
		Page<cliente> objCli = clienteService.findAll(pageRequest);		
		model.addAttribute("titulo", "Mantenimiento de Clientes");
		model.addAttribute("cliente", objCli); // Pasamos cliente a la vista
		
		pageRender<cliente> objPagRen = new pageRender<>("/listar", objCli); // pageRender es una clase que se encuentra en el paquete com.springboot.app.pageable
		model.addAttribute("page", objPagRen); // Pasamos page a la vista
		
		return "listar";
		
		/*
		model.addAttribute("titulo", "Mantenimiento de Clientes");
		model.addAttribute("cliente", clienteService.findAll());
		return "listar";
		*/
		
	}
	
	@RequestMapping(value="/form/{id}") // Con las llaves se le dice que es un dato dinámico
	public String buscar(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {		
		
		cliente obj = new cliente();
		obj = clienteService.findOne(id);
		
		if (obj == null) {
			flash.addFlashAttribute("error", "El ID del Cliente no existe en la BD");
			return "redirect:/listar";
		} else {		
			model.addAttribute("cliente", obj);
			return "form";
		}		
		
	}
		
	@RequestMapping(value="/form")
	public String crear(Model model) {
		cliente obj = new cliente();
		model.addAttribute("titulo", "Mantenimiento de Clientes");
		model.addAttribute("cliente", obj);				
		return "form";		
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid cliente obj, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) { // @Valid habilita la valicación en el objeto mapeado al Form	
						
		if (!foto.isEmpty()) {	// Si hay una foto o archivo cargado			
			// Metodo para guardar fotos o archivos en una ruta absoluta	
			String uniqueFilename = uploadService.copy(foto); // Acción
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'"); // Mensaje
			obj.setFoto(uniqueFilename); // Setea el nombre unico de la foto para despues guardarla			
			
			// Metodo para eliminar fotos viejas
			if (obj.getId() != null) { // Si el cliente existe			
				try {
					cliente obj2 = new cliente();			
					obj2 = clienteService.findOne(obj.getId());
					if (obj2.getFoto() != null && obj2.getFoto().length() > 0) {																
						
						uploadService.delete(obj2.getFoto());											
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		} else {
			if (obj.getId() != null) {
				cliente obj3 = new cliente();
				obj3 = clienteService.findOne(obj.getId());				
				obj.setFoto(obj3.getFoto());
			}
		}
		
		if (result.hasErrors()) {			
			model.addAttribute("titulo", "Mantenimiento de Clientes");
			return "form";	
		} else {
			String mensajeFlash = (obj.getId() == null) ? "Cliente creado con éxito" : "Cliente editado con éxito";
			
			clienteService.save(obj); // Guardar // Editar // C* R U* D // Acción
			status.setComplete(); // Luego de insertar el cliente, se completa la session y luego se elimine de la session
			flash.addFlashAttribute("success", mensajeFlash); // Mensaje
			return "redirect:/listar";
			
			/*
			if (obj.getId() == null) { // Si el cliente no existe
				flash.addFlashAttribute("success", "Cliente creado con éxito"); // Mensaje				
			} else { // Si el cliente existe
				flash.addFlashAttribute("success", "Cliente editado con éxito");				
			}	
			*/										
		}
		
	}	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		cliente obj = new cliente();
		obj = clienteService.findOne(id);
		
		if (obj == null) {
			flash.addFlashAttribute("error", "El ID del Cliente no existe en la BD");				
		} else {						
			clienteService.delete(obj); // C R U D* // Acción
			flash.addFlashAttribute("success", "Cliente eliminado con éxito"); // Mensaje

			if (uploadService.delete(obj.getFoto())) {				
				flash.addFlashAttribute("info", "Foto " + obj.getFoto() + " eliminada con exito");				
			}
		}	
		return "redirect:/listar";
		
	}
	
	/******************************/
	
	@GetMapping(value="/ver/{id}") // Notación corta para @RequestMapping(method=RequestMethod.GET)
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		
		cliente obj = new cliente(); 
		obj = clienteService.findOne(id);
		
		if (obj == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD");
			return "redirect:/listar";
		} else {
			model.addAttribute("cliente", obj);
			model.addAttribute("titulo", "Detalle cliente: " + obj.getNombre());
			return "ver";
		}		
		
	}	
		
	@GetMapping(value="/uploads/{filename:.+}") // Eliminamos .png o .jpg
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		// Metodo para que podamos obtener la ruta absoluta, para que se pueda obtener en detalle de clientes, en la plantilla ver, las fotos
		Resource recurso = uploadService.load(filename);		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
		
	}			
	
}
