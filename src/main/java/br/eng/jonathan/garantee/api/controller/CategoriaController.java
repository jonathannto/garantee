package br.eng.jonathan.garantee.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository repository;
	
	@GetMapping
	public List<Categoria> listar() {
		
		return repository.findAll();
	}
}
