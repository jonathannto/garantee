package br.eng.jonathan.garantee.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.eng.jonathan.garantee.api.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/categorias", produces = "application/json")
public class CategoriaController {

	private static final String CATEGORIA_BUSCA_CATEGORIA_ERRO = "CATEGORIA.BUSCA_CATEGORIA_ERRO";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CategoriaRepository repository;

	@GetMapping
	public List<Categoria> listar() {

		return repository.findAll();

	}

	@GetMapping("/{codigoCategoria}")
	public ResponseEntity<Optional<Categoria>> buscarPorCodigoCategoria(@PathVariable Long codigoCategoria) {

		var categoria = repository.findById(codigoCategoria);

		return ResponseEntity.ok(categoria);

	}

	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

		Categoria categoriaSalva = repository.save(categoria);

		 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigoCategoria}")
			.buildAndExpand(categoriaSalva.getCodigoCategoria()).toUri();

		 return ResponseEntity.created(uri).body(categoriaSalva);
	}

	@PutMapping("/{codigoCategoria}")
	public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long codigoCategoria, @Valid @RequestBody Categoria categoria) throws NotFoundException {

		Categoria categoriaSalva = repository.findById(codigoCategoria).orElseThrow(() -> new NotFoundException(getMessageErro(CATEGORIA_BUSCA_CATEGORIA_ERRO)));
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigoCategoria");

		repository.save(categoriaSalva);

		return ResponseEntity.ok(categoriaSalva);
	}

	@DeleteMapping("/{codigoCategoria}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCategoria(@PathVariable Long codigoCategoria){

		repository.deleteById(codigoCategoria);

	}

	private String getMessageErro(String mensagem) {
		return messageSource.getMessage(mensagem, null, LocaleContextHolder.getLocale());
	}

}
