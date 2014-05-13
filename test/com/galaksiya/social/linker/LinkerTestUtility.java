package com.galaksiya.social.linker;

import java.util.Arrays;
import java.util.List;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

public class LinkerTestUtility {

	/**
	 * This method tries to link all statements of given resource, and
	 * successfully links appropriate ones.
	 * 
	 * @param subjectRscToBeLinked
	 * @throws Exception
	 */
	public static void executeLinker(Resource subjectRscToBeLinked,
			Linker linker) throws Exception {
		// possible ranges...
		LinkageMap linkageMap = linker.getClass().getAnnotation(
				LinkageMap.class);
		List<String> rangeList = Arrays.asList(linkageMap.classes());
		List<String> predList = Arrays.asList(linkageMap.properties());
		// get all statements of subject resource
		List<Statement> stsmtList = subjectRscToBeLinked.getModel()
				.listStatements().toList();
		for (Statement statement : stsmtList) {
			// get object value of statment
			RDFNode object = statement.getObject();
			if (object.isResource()) {
				Resource objectRsc = object.asResource();
				// get rdf:type property value of object resource
				Resource rdfTypeRscValue = objectRsc
						.getPropertyResourceValue(RDF.type);
				// link object resource if it is appropriate for the linker...
				if (predList.contains(statement.getPredicate().getURI())
						&& rangeList.contains(rdfTypeRscValue.getURI())) {
					// link resource
					linker.link(statement);
				}
			}

		}
	}
}
