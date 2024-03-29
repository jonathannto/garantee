package br.eng.jonathan.garantee.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eng.jonathan.garantee.api.model.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
