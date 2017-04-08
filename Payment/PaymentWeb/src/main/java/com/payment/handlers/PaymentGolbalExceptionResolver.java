package com.payment.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class PaymentGolbalExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(PaymentGolbalExceptionResolver.class);
	
	final MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView  handleException(Exception exception) {
		log.error("exception to string  : ", exception);
		return new ModelAndView(jsonView,"errorMessage" ,exception.toString());

	}
}
