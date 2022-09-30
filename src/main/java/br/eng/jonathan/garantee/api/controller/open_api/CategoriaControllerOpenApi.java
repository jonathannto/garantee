package br.eng.jonathan.garantee.api.controller.open_api;

import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.model.dto.CategoriaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "Categoria", description = "Implementa as APIs para controle e cadastros de categoria")
public interface CategoriaControllerOpenApi {

    @ApiOperation(value = "Listar todas as categorias", notes = "Realiza recuperção de todas as categorias cadastrados dentro do sistema")
    public ResponseEntity<List<CategoriaDTO>> listar();

    @ApiOperation(value = "Busca uma única categoria", notes = "Realiza recuperção de uma única categoria de dentro do sistema")
    public ResponseEntity<CategoriaDTO> buscarPorCategoria (@PathVariable Long codigoCategoria) throws NotFoundException;

    @ApiOperation(value = "Cria uma nova categoria", notes = "Realiza o cadastramento de uma nova categoria dentro do sistema")
    public ResponseEntity<CategoriaDTO> criarCategoria(CategoriaDTO categoriaDTO, HttpServletResponse response);

    @ApiOperation(value = "Atualiza uma categoria específica", notes = "Realiza a atualização de uma categoria dentro do sistema")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(Long codigoCategoria, CategoriaDTO categoriaDTO) throws NotFoundException;

    @ApiOperation(value = "Deleta uma categoria específica pelo seu código", notes = "Realiza a exclusão de todas as categorias dentro do sistema")
    public void deletarCategoria( Long codigoCategoria);

}
