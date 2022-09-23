package br.eng.jonathan.garantee.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.eng.jonathan.garantee.api.event.RecursoCriadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/categorias", produces = "application/json")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

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
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

			Categoria categoriaSalva = repository.save(categoria);

			publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigoCategoria()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
}
