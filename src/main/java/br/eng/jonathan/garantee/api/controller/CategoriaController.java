package br.eng.jonathan.garantee.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.eng.jonathan.garantee.api.exception.NotFoundException;
import org.flywaydb.core.api.resource.Resource;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
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

		 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigoCategoria}")
			.buildAndExpand(categoriaSalva.getCodigoCategoria()).toUri();

		 return ResponseEntity.created(uri).body(categoriaSalva);
	}

	@PutMapping("/{codigoCategoria}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long codigoCategoria, @Valid @RequestBody Categoria categoria) throws NotFoundException {

		Categoria categoriaSalva = repository.findById(codigoCategoria).orElseThrow(() -> new NotFoundException("categoria não encontrada."));
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigoCategoria");

		repository.save(categoriaSalva);

		return ResponseEntity.ok(categoriaSalva);
	}

	@DeleteMapping("/{codigoCategoria}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigoCategoria){
		repository.deleteById(codigoCategoria);
	}
}
