package br.eng.jonathan.garantee.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MensagemErro {
	
	private String mensagemUsuario;
	
	private String mensagemDesenvolvedor;

}
