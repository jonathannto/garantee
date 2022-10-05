package br.eng.jonathan.garantee.api.model;

import javax.persistence.*;
import javax.validation.constraints.*;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_categoria")
	private Long codigoCategoria;

	@NotNull
	@NotBlank
	@Size(min = 3, max = 45)
	@Column(name = "nome")
	private String nome;
	
}
