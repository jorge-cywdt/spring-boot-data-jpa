package com.springboot.app;

//import java.nio.file.Paths;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	/*
	private final Logger log = LoggerFactory.getLogger(getClass());
		
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// Ruta absoluta, para que se pueda obtener en el detalle del cliente las imágenes
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();				
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);		
		log.info("resourcePath: " + resourcePath);
	}
	*/
	
}
