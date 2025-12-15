package com.example.ecommerce.exception;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("timestamp", new Date());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).toList();

		body.put("message", "Validation failed");
		body.put("errors", errors);
		body.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAll(Exception ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
