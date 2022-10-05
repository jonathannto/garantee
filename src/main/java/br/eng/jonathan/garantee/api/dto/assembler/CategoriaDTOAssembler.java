package br.eng.jonathan.garantee.api.dto.assembler;

import br.eng.jonathan.garantee.api.dto.CategoriaDTO;
import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.model.Categoria;
import br.eng.jonathan.garantee.api.service.CategoriaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoriaService service;

    public CategoriaDTO converterParaDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = modelMapper.map(categoria, CategoriaDTO.class);

        return categoriaDTO;
    }

    public Categoria converterParaEntidade(CategoriaDTO categoriaDTO) throws NotFoundException {
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);

        if(categoriaDTO.getCodigoCategoria() != null) {
            Categoria categoriaAntiga = service.buscarPorCodigoCategoria(categoriaDTO.getCodigoCategoria());
            categoria.setCodigoCategoria(categoriaAntiga.getCodigoCategoria());
            categoria.setNome(categoriaAntiga.getNome());
        }

        return categoria;
    }

}
