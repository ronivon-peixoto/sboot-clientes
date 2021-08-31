package io.builders.sbootclientes.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
	public final ResponseEntity<ExceptionResponse> handlerInternalServerError(Exception ex, WebRequest request) {
		return new ResponseEntity<>(ExceptionResponse.builder()
				.timestamp(new Date())
				.message(ex.getMessage())
				.details(request.getDescription(false))
				.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlerResourceNotFound(Exception ex, WebRequest request) {
		return new ResponseEntity<>(ExceptionResponse.builder()
				.timestamp(new Date())
				.message(ex.getMessage())
				.details(request.getDescription(false))
				.build(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(e -> errors.put(e.getPropertyPath().toString(), e.getMessage()));
		return new ResponseEntity<>(ValidationExceptionResponse.builder()
				.timestamp(new Date())
				.fieldErrors(errors)
				.details(request.getDescription(false))
				.build(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(e -> errors.put(((FieldError) e).getField(), e.getDefaultMessage()));
		return new ResponseEntity<>(ValidationExceptionResponse.builder()
				.timestamp(new Date())
				.fieldErrors(errors)
				.details(request.getDescription(false))
				.build(), HttpStatus.BAD_REQUEST);
	}

}
