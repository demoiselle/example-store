package org.demoiselle.jee7.example.store.user.exception;

import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.exception.ExceptionTreatment;
import org.demoiselle.jee.rest.exception.treatment.ExceptionTreatmentImpl;

@Alternative
public class ExceptionTreatmentApp implements ExceptionTreatment {

	@Inject
	private ExceptionTreatmentImpl exceptionTreatment;

	private static final Logger logger = Logger.getLogger(ExceptionTreatmentApp.class.getName());

	@Override
	public Response getFormatedError(Throwable exception, HttpServletRequest request) {
		logger.info("Using ExceptionTreatmentApp");
		return exceptionTreatment.getFormatedError(exception, request);
	}

}
