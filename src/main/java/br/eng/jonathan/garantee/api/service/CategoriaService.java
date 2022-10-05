package br.eng.jonathan.garantee.api.service;

import br.eng.jonathan.garantee.api.dto.CategoriaDTO;
import br.eng.jonathan.garantee.api.dto.assembler.CategoriaDTOAssembler;
import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class CategoriaService {

    private static final String CATEGORIA_BUSCA_CATEGORIA_ERRO = "CATEGORIA.BUSCA_CATEGORIA_ERRO";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CategoriaDTOAssembler assemblerDTO;

    @GetMapping
    public List<Categoria> listar() {

        return repository.findAll();

    }

    public Categoria buscarPorCodigoCategoria(@PathVariable Long codigoCategoria) throws NotFoundException {

        Categoria categoria = repository.findById(codigoCategoria).orElseThrow(() -> new NotFoundException(getMessageErro(CATEGORIA_BUSCA_CATEGORIA_ERRO)));

        return categoria;

    }

    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) throws NotFoundException {

        Categoria categoriaSalva = repository.save(assemblerDTO.converterParaEntidade(categoriaDTO));

        return assemblerDTO.converterParaDTO(categoriaSalva);

    }

    public Categoria atualizarCategoria(Long codigoCategoria, Categoria categoria) {

        Categoria categoriaSalva = repository.findById(codigoCategoria).orElseThrow(() -> new NotFoundException(getMessageErro(CATEGORIA_BUSCA_CATEGORIA_ERRO)));
        BeanUtils.copyProperties(categoria, categoriaSalva, "codigoCategoria");

        repository.save(categoriaSalva);

        return categoriaSalva;

    }

    public void deletarCategoria( Long codigoCategoria){

        repository.deleteById(codigoCategoria);

    }

    private String getMessageErro(String mensagem) {
        return messageSource.getMessage(mensagem, null, LocaleContextHolder.getLocale());
    }

}
