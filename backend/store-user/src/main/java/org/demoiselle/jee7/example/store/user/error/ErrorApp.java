package org.demoiselle.jee7.example.store.user.error;

import javax.enterprise.inject.Alternative;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.core.api.error.ErrorTreatment;

@Alternative
public class ErrorApp implements ErrorTreatment {

	@Override
	public Response getFormatedError(Exception exception, HttpServletRequest request) {
		return Response.serverError().build();
	}

}
