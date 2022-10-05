package br.eng.jonathan.garantee.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.awt.event.FocusEvent;
import java.util.Objects;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException  extends RuntimeException {

    public NotFoundException() {
        super();
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(Throwable cause) {
        super(cause);
    }


}
