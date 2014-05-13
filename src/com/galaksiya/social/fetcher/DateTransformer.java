package com.galaksiya.social.fetcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class DateTransformer {

	/**
	 * This method splits given American date formatted String text into its
	 * tokens and transforms it to Calendar format.
	 * 
	 * @param textDate
	 * @return
	 */
	public static Calendar transformAmericanTextdateToCalendar(String textDate) {
		// split into parts as tokens given
		StringTokenizer tokenizer = new StringTokenizer(textDate, "/,:.;-");
		List<Integer> datePartList = new ArrayList<Integer>();
		while (tokenizer.hasMoreTokens()) {
			// transform string type to integer
			datePartList.add(Integer.parseInt(tokenizer.nextToken().trim()));
		}
		// generate Calendar date
		if (datePartList.size() == 3) {
			return new GregorianCalendar(datePartList.get(2),
					datePartList.get(0) - 1, datePartList.get(1));
		}
		return null;
	}

	/**
	 * This method transforms given partial Date into Calendar format.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static Calendar transformPartialDateToCalendarFormat(Object day,
			Object month, Object year) {
		// set default value if the given is null...
		if (day == null) {
			day = "1";
		}
		if (month == null) {
			month = "1";
		}
		if (year == null) {
			year = "0";
		}
		// get String format of date parts
		String textDay = day.toString();
		String textMonth = month.toString();
		String textYear = year.toString();

		// get int Format of String date parts
		int intDay = Integer.parseInt(textDay);
		int intMonth = Integer.parseInt(textMonth);
		int intYear = Integer.parseInt(textYear);

		// create new Calendar instance using integer day parts
		return new GregorianCalendar(intYear, intMonth - 1, intDay);
	}
}
