package com.galaksiya.social.ontology.samplequery;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SampleQueryOnIntegratedDataTest.class,
		FacebookSampleQueryTest.class, FoursquareSampleQueryTest.class,
		LinkedinSampleQueryTest.class })
public class AllSampleQueryTests {
}
