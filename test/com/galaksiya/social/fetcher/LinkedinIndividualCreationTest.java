package com.galaksiya.social.fetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.entity.LinkedInPerson;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.LinkedinTestVocabulary;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * TODO Linkedin'de bilgisi değişmeyen test kullanıcısı açılmalı!
 * 
 * @author galaksiya
 * 
 */
public class LinkedinIndividualCreationTest {

	private static LinkedinIndividualCreator linkedinIndividualCreator;
	private static Resource linkedinIndividual;
	private static LinkedInPerson linkedinPerson;

	@BeforeClass
	public static void beforeClass() throws Exception {
		linkedinIndividualCreator = new LinkedinIndividualCreator(
				CommonTestVocabulary.BASE_URI);
		linkedinPerson = TestUtil.createLinkedinPersonGeorge();
		linkedinPerson.setId(LinkedinTestVocabulary.SAMPLE_LINKEDIN_ID);
		// creating Linkedin individual
		linkedinIndividual = linkedinIndividualCreator
				.createPersonIndividual(linkedinPerson);
	}

	/**
	 * creating and checking properties of a linkedin individual
	 * 
	 * @throws Exception
	 */
	@Test
	public void linkedinIndividualCreation() throws Exception {
		assertNotNull(linkedinIndividual);

		/**
		 * check user URI is correct
		 */
		assertEquals(CommonTestVocabulary.BASE_URI
				+ LinkedinTestVocabulary.SAMPLE_LINKEDIN_ID,
				linkedinIndividual.getURI());

		// checking rdf:type property
		Statement rdfTypeStmt = linkedinIndividual.getProperty(RDF.type);
		assertNotNull(rdfTypeStmt);
		RDFNode rdfTypeNode = rdfTypeStmt.getObject();
		assertNotNull(rdfTypeNode);
		assertEquals(FOAF.Person, rdfTypeNode);

		// checking foaf:name property
		Statement nameStmt = linkedinIndividual.getProperty(FOAF.name);
		assertNotNull(nameStmt);
		RDFNode nameNode = nameStmt.getObject();
		assertNotNull(nameNode);
		String nameValue = nameNode.asLiteral().getString();
		assertEquals(LinkedinTestVocabulary.PERSON_NAME, nameValue);

		// checking foaf:name property
		Statement depictionStmt = linkedinIndividual
				.getProperty(FOAF.depiction);
		assertNotNull(depictionStmt);
		RDFNode depictionNode = depictionStmt.getObject();
		assertNotNull(depictionNode);
		assertEquals(LinkedinTestVocabulary.DEPICTION_VALUE, depictionNode
				.asResource().getURI());

		// checking rdfs:label property
		Statement labelStmt = linkedinIndividual.getProperty(RDFS.label);
		assertNotNull(labelStmt);
		RDFNode labelNode = labelStmt.getObject();
		assertNotNull(labelNode);
		String labelValue = labelNode.asLiteral().getString();
		assertEquals(LinkedinTestVocabulary.LABEL_VALUE, labelValue);

		// checking cv:Industry property
		Statement industryStmt = linkedinIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.CV_INDUSTRY_PRP_URI));
		assertNotNull(industryStmt);
		RDFNode industryNode = industryStmt.getObject();
		assertNotNull(industryNode);
		String industryValue = industryNode.asLiteral().getString();
		assertEquals(LinkedinTestVocabulary.INDUSTRY_VALUE, industryValue);

		// checking linkedin:headline property
		Statement headlineStmt = linkedinIndividual.getProperty(ResourceFactory
				.createProperty(LinkedinOntologyVocabulary.HEADLINE_URI));
		assertNotNull(headlineStmt);
		RDFNode headlineNode = headlineStmt.getObject();
		assertNotNull(headlineNode);
		String headlineValue = headlineNode.asLiteral().getString();
		assertEquals(LinkedinTestVocabulary.HEADLINE_VALUE, headlineValue);

		/**
		 * check user has an linkedin account
		 */
		// get account property resource value and check it whether it is not
		// null first.
		Resource accountRsc = linkedinIndividual
				.getPropertyResourceValue(CommonOntologyVocabulary.ACCOUNT_PRP);
		assertNotNull(accountRsc);

		// get FOAF:accountServiceHomepage property value of account resource
		Resource accountHomepageRsc = accountRsc
				.getPropertyResourceValue(FOAF.accountServiceHomepage);
		assertNotNull(accountHomepageRsc);
		assertEquals(LinkedinOntologyVocabulary.LINKEDIN_HOMEPAGE_RSC,
				accountHomepageRsc);

		// get FOAF.accountName property value of account and check it as object
		// and check after converting literal value.
		RDFNode accountIdRsc = accountRsc.getProperty(FOAF.accountName)
				.getObject();
		assertNotNull(accountIdRsc);
		String accountIdValue = accountIdRsc.asLiteral().getString();
		assertEquals(linkedinPerson.getPerson().getId(), accountIdValue);

	}

	/**
	 * checking work history properties of a linkedin individual
	 * 
	 * @throws Exception
	 */
	@Test
	public void workHistoryCreation() throws Exception {
		StmtIterator workHisPrpIter = linkedinIndividual
				.listProperties(ResourceFactory
						.createProperty(CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI));
		assertNotNull(workHisPrpIter);
		List<Statement> workHisList = workHisPrpIter.toList();
		assertEquals(2, workHisList.size());

		List<String> companyNames = Arrays
				.asList(LinkedinTestVocabulary.COMPANIES);

		List<String> titles = Arrays.asList(LinkedinTestVocabulary.TITLES);

		// checking work history list
		for (Statement workHisStmt : workHisList) {
			RDFNode workHisNode = workHisStmt.getObject();
			assertNotNull(workHisNode);
			// checking job titles
			Statement jobTitleStmt = workHisNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.CV_TITLE_URI));
			assertNotNull(jobTitleStmt);
			RDFNode jobTitleNode = jobTitleStmt.getObject();
			String jobTitleValue = jobTitleNode.asLiteral().getString();
			assertTrue(titles.contains(jobTitleValue));

			// checking company names
			Statement companyStmt = workHisNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.EMPLOYED_IN_PRP_URI));
			assertNotNull(companyStmt);
			RDFNode companyNode = companyStmt.getObject();
			assertNotNull(companyNode);
			Statement companyNameStmt = companyNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(companyNameStmt);
			RDFNode companyNameNode = companyNameStmt.getObject();
			String companyNameValue = companyNameNode.asLiteral().getString();
			assertTrue(companyNames.contains(companyNameValue));

		}

	}

}
