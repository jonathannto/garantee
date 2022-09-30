package br.eng.jonathan.garantee.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.eng.jonathan.garantee.api.exception.NotFoundException;
import br.eng.jonathan.garantee.api.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GaranteeExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());

		String mensagemDesenvolvedor = ex.getCause().toString();

		List<MensagemErro> erros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<MensagemErro> errors = criarListaDeErros(ex.getBindingResult());

		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);

	}



	private List<MensagemErro> criarListaDeErros(BindingResult bindingResult) {

		List<MensagemErro> errors = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagensUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagensDesenvolvedor = fieldError.toString();
			
			errors.add(new MensagemErro(mensagensUsuario, mensagensDesenvolvedor));
		}
		
		return errors;
	}

	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){

		String mensagensUsuario = messageSource.getMessage("recurso.nao_encontrado", null, LocaleContextHolder.getLocale());
		String mensagensDesenvolvedor = ex.toString();

		List<MensagemErro> errors = Arrays.asList(new MensagemErro(mensagensUsuario, mensagensDesenvolvedor));

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
		String mensagensUsuario = ex.getMessage().toString();
		String mensagensDesenvolvedor = ex.toString();

		List<MensagemErro> errors = Arrays.asList(new MensagemErro(mensagensUsuario, mensagensDesenvolvedor));

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}
