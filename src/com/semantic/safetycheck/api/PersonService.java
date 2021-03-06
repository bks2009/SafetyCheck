package com.semantic.safetycheck.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;

import com.semantic.safetycheck.app.SafetyCheckServlet;
import com.semantic.safetycheck.dao.PersonDAO;
import com.semantic.safetycheck.pojo.Person;

@Path("/persons")
public class PersonService extends SCService {
	
	private PersonDAO dao = new PersonDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response persons(@Context UriInfo info) {
		List<Person> person = null;
		Boolean success = Boolean.TRUE;
		String msg = "";
		String alertId = info.getQueryParameters().getFirst("alertId");
		try{
			if( null != alertId && StringUtils.isNoneEmpty(alertId)){
				person = dao.getPersonsImpacted(SafetyCheckServlet.defaultNameSpace + alertId);
				
			}
			else{
				 person = dao.getAllPersons();
			}
		}
	   catch (Exception e) {
		    success = Boolean.FALSE;
			msg = e.getMessage();
		}
		String response = getResponse(success, person, msg);
		return Response.status(200).entity(response).build();
	}
	
	
}