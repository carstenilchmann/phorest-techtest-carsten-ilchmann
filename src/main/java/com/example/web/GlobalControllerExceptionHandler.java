package com.example.web;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseBody ErrorInfo handleResourceNotFound(HttpServletRequest req, Exception ex) {
		return new ErrorInfo("Resource not found");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody ErrorInfo handleEntityNotFound(HttpServletRequest req, Exception ex) {
		return new ErrorInfo("Resource not found");
	}

}
