package org.demoiselle.jee7.example.store.user.exception;

import java.util.logging.Logger;

import javax.enterprise.inject.Specializes;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.rest.exception.treatment.ExceptionTreatmentImpl;

@Specializes
public class ExceptionTreatmentApp extends ExceptionTreatmentImpl {

	private static final Logger logger = Logger.getLogger(ExceptionTreatmentApp.class.getName());

	@Override
	public Response getFormatedError(Throwable exception, HttpServletRequest request) {
		logger.info("Using ExceptionTreatmentApp");
		return super.getFormatedError(exception, request);
	}

}
