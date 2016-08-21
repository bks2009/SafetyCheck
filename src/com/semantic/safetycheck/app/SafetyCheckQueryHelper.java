package com.semantic.safetycheck.app;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class SafetyCheckQueryHelper {

	static String defaultNameSpace = "http://www.semanticweb.org/ontologies/2015/10/SafetyCheck#";

	public static ResultSet runQuery(String queryRequest) {

		StringBuffer queryStr = new StringBuffer();

		// Establish Prefixes
		// Set default Name space first
		queryStr.append("PREFIX sc" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <"
				+ "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <"
				+ "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");

		// Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/sc/query", query);
		ResultSet response = null;
		try {
			response = qexec.execSelect();
		} finally {
			 //qexec.close();
		}
		/*
		Dataset dataset = SafetyCheckServlet.store.dataset;
		dataset.begin(ReadWrite.READ) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		ResultSet response = null;
		 try {
		response = qexec.execSelect();
		 } finally {
			 dataset.commit();
			 dataset.end();
		 //qexec.close();
		 }
		List response = new ArrayList();
		try {
			ResultSet rs = qexec.execSelect();
			while (rs.hasNext()) {
				QuerySolution soln = rs.nextSolution();
				Object obj = qsp.process(soln);
				if (obj != null) {
					response.add(obj);
				}

			}
		} finally {
			 qexec.close();
		}
		 */
		return response;
	}

}