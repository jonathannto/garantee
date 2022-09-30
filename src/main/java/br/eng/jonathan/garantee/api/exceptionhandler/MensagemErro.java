package br.eng.jonathan.garantee.api.exceptionhandler;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemErro {
	
	private String mensagemUsuario;
	
	private String mensagemDesenvolvedor;

}
