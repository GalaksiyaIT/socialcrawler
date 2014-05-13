package com.galaksiya.social.linker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;

@Ignore
public class BankPersonLinkerTest {

	/**
	 * This test creates a sample person to check link it with bank dataset.
	 * 
	 * @throws Exception
	 */
	@Test
	public void linkSocialPersonToBank() throws Exception {

		// create sample resource
		Resource socialRsc = ModelFactory.createDefaultModel().createResource(
				"http://seagent.ege.edu.tr/resource/burakyonyul123");
		// add FOAF:name property
		socialRsc.addProperty(FOAF.name, "Burak Yönyül");
		// try to link person
		new BankPersonLinker().linkPersonToBankDataset(socialRsc);
		// check whether link has been successfully created.
		assertTrue(socialRsc.hasProperty(OWL.sameAs, ResourceFactory
				.createResource("http://example.com/bank/resource/Person1")));

	}

	/**
	 * This test creates a sample person to check not to link it with bank
	 * dataset.
	 * 
	 * @throws Exception
	 */
	@Test
	public void dontLinkSocialPersonToBank() throws Exception {

		// create sample resource
		Resource socialRsc = ModelFactory.createDefaultModel().createResource(
				"http://seagent.ege.edu.tr/resource/burakyonyul123");
		// add FOAF:name property
		socialRsc.addProperty(FOAF.name, "Burak Yonyul");
		// try to link person
		new BankPersonLinker().linkPersonToBankDataset(socialRsc);
		// check whether link has not been created.
		assertFalse(socialRsc.hasProperty(OWL.sameAs, ResourceFactory
				.createResource("http://example.com/bank/resource/Person1")));

	}

}
