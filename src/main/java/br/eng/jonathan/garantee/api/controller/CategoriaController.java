package br.eng.jonathan.garantee.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import br.eng.jonathan.garantee.api.controller.open_api.CategoriaControllerOpenApi;
import br.eng.jonathan.garantee.api.dto.CategoriaDTO;
import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.service.CategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.eng.jonathan.garantee.api.model.Categoria;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categorias", produces = "application/json")
public class CategoriaController implements CategoriaControllerOpenApi {

	@Autowired
	private CategoriaService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listar() {

		List<Categoria> categorias = service.listar();

		return ResponseEntity.ok(categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList()));

	}

	@GetMapping("/{codigoCategoria}")
	public ResponseEntity<CategoriaDTO> buscarPorCategoria (@PathVariable Long codigoCategoria) throws NotFoundException {

		var categoria = service.buscarPorCodigoCategoria(codigoCategoria);

		return ResponseEntity.ok()
				.body(modelMapper.map(categoria, CategoriaDTO.class));

	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaDTO categoriaDTO, HttpServletResponse response) throws NotFoundException {

		var categoriaSalva = service.criarCategoria(categoriaDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigoCategoria}")
				.buildAndExpand(categoriaSalva.getCodigoCategoria()).toUri();

		return ResponseEntity.created(uri).body(modelMapper.map(categoriaSalva, CategoriaDTO.class));


	}

	@PutMapping("/{codigoCategoria}")
	public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long codigoCategoria, @Valid @RequestBody CategoriaDTO categoriaDTO) {

		Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
		Categoria categoriaSalva = service.atualizarCategoria(codigoCategoria, categoria);

		return ResponseEntity.ok(modelMapper.map(categoriaSalva, CategoriaDTO.class));
	}

	@DeleteMapping("/{codigoCategoria}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCategoria(@PathVariable Long codigoCategoria){

		service.deletarCategoria(codigoCategoria);

	}

}
