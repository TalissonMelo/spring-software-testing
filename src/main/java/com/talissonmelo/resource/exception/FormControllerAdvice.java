package com.talissonmelo.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.talissonmelo.service.exception.DataViolation;
import com.talissonmelo.service.exception.EntityNotFound;
import com.talissonmelo.service.exception.RuleException;

@ControllerAdvice
public class FormControllerAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DataViolation.class)
	public ResponseEntity<StandardError> dataViolation(DataViolation ex, HttpServletRequest request){
		StandardError error = StandardError.builder().message(ex.getMessage()).status(HttpStatus.CONFLICT.value()).build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<StandardError> notFound(EntityNotFound ex, HttpServletRequest request){
		StandardError error = StandardError.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND.value()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(RuleException.class)
	public ResponseEntity<StandardError> rule(RuleException ex, HttpServletRequest request){
		StandardError error = StandardError.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST.value()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		body = StandardError.builder().status(status.value()).message(status.getReasonPhrase()).build();


		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
}
