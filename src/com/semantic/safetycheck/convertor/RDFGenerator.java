package com.semantic.safetycheck.convertor;

import java.util.ArrayList;

public class RDFGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RDFGenerator().createRDF();
	}

	public void createRDF() {
		ArrayList<ArrayList<String>> rowCols = CSVManager.loadCSV();
		StringBuffer rdf = new StringBuffer(100);
		rdf.append("<rdf:RDF\n");
		rdf.append("	xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n");
		rdf.append("	xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n");
		rdf.append("	xmlns:owl=\"http://www.w3.org/2002/07/owl#\" \n");
		rdf.append("	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" \n");
		rdf.append("	xmlns:sc=\"http://www.semanticweb.org/ontologies/2015/10/SafetyCheck#\">\n\n");

		rdf.append(populateEarthquake(rowCols));

		rdf.append("</rdf:RDF>");

		System.out.println(rdf.toString());
	}

	/**
	 * <NamedIndividual rdf:about="&sc;earthquake1"> <rdf:type rdf:resource=
	 * "&sc;StrongEarthquake"/> <sc:hasMagnitude rdf:datatype="&xsd;float">4.5
	 * </sc:hasMagnitude>
	 * <sc:atLatitude rdf:datatype="&xsd;float">80.0</sc:atLatitude>
	 * <sc:atLongitude rdf:datatype="&xsd;float">90.0</sc:atLongitude>
	 * </NamedIndividual>
	 * 
	 * @param rowCols
	 * @return
	 */
	private String populateEarthquake(ArrayList<ArrayList<String>> rowCols) {
		StringBuffer rdf = new StringBuffer(100);
		for (int x = 1; x < rowCols.size(); x++) {
			ArrayList<String> row = rowCols.get(x);
			rdf.append("	<NamedIndividual rdf:about=\"&sc;earthquake" + x + "\">\n");
			double mag = Double.parseDouble(row.get(4));
			if (mag > 4) {
				rdf.append("		<rdf:type rdf:resource=\"&sc;StrongEarthquake\"/>\n");
			} else {
				rdf.append("		<rdf:type rdf:resource=\"&sc;WeakEarthquake\"/>\n");
			}
			rdf.append("		<sc:hasMagnitude rdf:datatype=\"&xsd;float\">" + mag + "</sc:hasMagnitude>\n");
			rdf.append("		<sc:atLatitude rdf:datatype=\"&xsd;float\">" + Double.parseDouble(row.get(1)) + "</sc:atLatitude>\n");
			rdf.append("		<sc:atLongitude rdf:datatype=\"&xsd;float\">" + Double.parseDouble(row.get(2)) + "</sc:atLongitude>\n");
			rdf.append("	</NamedIndividual>\n\n");

		}

		return rdf.toString();
	}

}