package com.springboot.app.pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class pageRender<T> {

	private String url;
	private Page<T> page;	
	private int numeroElementosPorPagina;
	private int totalPaginas;
	private int paginaActual;
	private List<pageItem> paginas;
	
	public pageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<pageItem>();
		
		numeroElementosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;
		
		int desde, hasta;
		if(totalPaginas <= numeroElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} 
		else {
			if(paginaActual <= numeroElementosPorPagina / 2) {
				desde = 1;
				hasta = numeroElementosPorPagina;
			} 
			else if(paginaActual >= totalPaginas - numeroElementosPorPagina / 2) {
				desde = totalPaginas - numeroElementosPorPagina + 1;
				hasta = numeroElementosPorPagina;
			} 
			else {
				desde = paginaActual -numeroElementosPorPagina / 2;
				hasta = numeroElementosPorPagina;
			}
		}
		
		for(int i=0; i<hasta; i++) {
			paginas.add(new pageItem(desde + i, paginaActual == desde + i));
		}

	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<pageItem> getPaginas() {
		return paginas;
	}
	
	/************************************************************/
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
}
