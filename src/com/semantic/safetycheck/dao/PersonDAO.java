package com.semantic.safetycheck.dao;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.semantic.safetycheck.app.SafetyCheckQueryHelper;
import com.semantic.safetycheck.pojo.Person;

public class PersonDAO {
	
	public List<Person> getAllPersons() {
		
		QueryExecution qexec = SafetyCheckQueryHelper.buildQuery(" select ?person ?name ?location ?region ?point ?lat ?lon where"
		+ " { ?person rdf:type sc:Person. ?person sc:hasName ?name ."
		+ " ?person sc:hasLocation ?location. OPTIONAL"
		+ " { ?person sc:locatedAt ?region. ?region sc:hasPoint ?point."
		+ " ?point sc:hasLatitude ?lat. ?point sc:hasLongitude ?lon.} }");
		List<Person> persons = new ArrayList<Person>();
		ResultSet rs = qexec.execSelect();
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			String name ="";
			String location = "";
			Float latitude = new Float(0);
			Float longitude = new Float(0);
			RDFNode person = soln.get("?person");
			
			if (person != null) {
				if(null != soln.getLiteral("?name"))
					name = soln.getLiteral("?name").getString();
				if(null != soln.getLiteral("?location"))
					location = soln.getLiteral("?location").getString();
				if(null != soln.getLiteral("?lat"))
					latitude = soln.getLiteral("?lat").getFloat();
				if(null != soln.getLiteral("?lon"))
					longitude = soln.getLiteral("?lon").getFloat();
					
				
				persons.add(new Person(person.toString(), name, location, latitude,
						longitude));
			}
		}
		qexec.close();
		return persons;
	}
	
	public List<Person> getPersonsImpacted(String alertId) {
	
		List<Person> persons = new ArrayList<Person>();
		QueryExecution qexec = SafetyCheckQueryHelper.buildQuery(
				" select ?person ?name ?location ?region ?point ?lat ?lon "
						+ " where { ?person sc:isImpactedBy <"
						+ alertId
						+ ">. ?person sc:hasName ?name. ?person sc:hasLocation ?location."
						+ " ?person sc:locatedAt ?region.  ?region sc:hasPoint ?point."
						+ " ?point sc:hasLatitude ?lat. ?point sc:hasLongitude ?lon. }");
		ResultSet rs = qexec.execSelect();
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			RDFNode person = soln.get("?person");
			if (person != null) {

				persons.add(new Person(person.toString(), soln.getLiteral("?name").getString(), soln.getLiteral("?location").getString(),
						soln.getLiteral("?lat").getFloat(), soln.getLiteral("?lon").getFloat() ));
				}
		}
		qexec.close();
		return persons;
	}


}
