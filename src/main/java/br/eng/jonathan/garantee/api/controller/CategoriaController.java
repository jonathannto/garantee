package br.eng.jonathan.garantee.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping("/{codigoCategoria}")
	public ResponseEntity<Optional<Categoria>> buscarPorCodigo(@PathVariable Long codigoCategoria) {
		
		var categoria = repository.findById(codigoCategoria);
		
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriaSalva = repository.save(categoria);
		
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigoCategoria}")
			.buildAndExpand(categoriaSalva.getCodigoCategoria()).toUri();
		
		 response.setHeader("Location", uri.toASCIIString());
		 
		 return ResponseEntity.created(uri).body(categoriaSalva);
	}
}
