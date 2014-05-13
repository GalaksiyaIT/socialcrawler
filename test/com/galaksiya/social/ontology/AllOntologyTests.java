package com.galaksiya.social.ontology;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.galaksiya.social.ontology.samplequery.AllSampleQueryTests;

@RunWith(Suite.class)
@SuiteClasses({ CommonOntologyCreationTest.class,
		CVextendingOntologyCreationTest.class,
		LGDOExtendedOntologyCreationTest.class,
		TagExtendingOntologyCreationTest.class,
		FacebookOntologyCreationTest.class,
		FoursquareOntologyCreationTest.class,
		LinkedinOntologyCreationTest.class, AllSampleQueryTests.class })
public class AllOntologyTests {
}
