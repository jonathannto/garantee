package br.eng.jonathan.garantee.api.service;

import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private static final String CATEGORIA_BUSCA_CATEGORIA_ERRO = "CATEGORIA.BUSCA_CATEGORIA_ERRO";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoriaRepository repository;

    @GetMapping
    public List<Categoria> listar() {

        return repository.findAll();

    }

    public Categoria buscarPorCodigoCategoria(@PathVariable Long codigoCategoria) throws NotFoundException {

        Categoria categoria = repository.findById(codigoCategoria).orElseThrow(() -> new NotFoundException(getMessageErro(CATEGORIA_BUSCA_CATEGORIA_ERRO)));

        return categoria;

    }

    public Categoria criarCategoria(Categoria categoria) {

        Categoria categoriaSalva = repository.save(categoria);
        return categoriaSalva;

    }

    public Categoria atualizarCategoria(Long codigoCategoria, Categoria categoria) throws NotFoundException {

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
