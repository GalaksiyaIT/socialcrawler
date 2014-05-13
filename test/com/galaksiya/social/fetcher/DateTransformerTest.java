package com.galaksiya.social.fetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateTransformerTest {

	/**
	 * Test Text base date transforming
	 * 
	 * @throws Exception
	 */
	@Test
	public void transformCompleteTextdateToCalendarFormat() throws Exception {
		// create expected calendar date
		Calendar expectedDate = new GregorianCalendar(1988, 7, 18);

		// transform same American formatted String date to Calendar format
		String textDate = "08/18/1988";
		Calendar actualDate = DateTransformer
				.transformAmericanTextdateToCalendar(textDate);
		// check whether they are equal
		assertNotNull(actualDate);
		assertEquals(expectedDate, actualDate);
	}

	/**
	 * Test partial date transforming
	 * 
	 * @throws Exception
	 */
	@Test
	public void transformPartialTextdateToDateFormat() throws Exception {
		// create expected calendar date
		Calendar expectedDate = new GregorianCalendar(1988, 7, 18);
		// tranform non-Text numerical date into Calendar Format
		long day = 18, month = 8, year = 1988;
		Calendar actualDate = DateTransformer
				.transformPartialDateToCalendarFormat(day, month, year);
		// check whether they are equal
		assertNotNull(actualDate);
		assertEquals(expectedDate, actualDate);

	}

}
