package com.galaksiya.social;

import static org.junit.Assert.*;

import org.junit.Test;

import com.galaksiya.social.utility.HashGenerator;

public class HashGeneratorTest {

	@Test
	public void generateHashCodeXor() throws Exception {

		// define inputs to generate hash id
		String firstInput = "firstInput", secondInput = "secondInput", thirdInput = "thirdInput";

		// check hash generator generates same value for same inputs
		assertEquals(
				HashGenerator.hashCode(firstInput, secondInput, thirdInput),
				HashGenerator.hashCode(firstInput, secondInput, thirdInput));

	}

	@Test
	public void generateHashCodeMd5() throws Exception {

		// define inputs to generate hash id
		String firstInput = "firstInput", secondInput = "secondInput", thirdInput = "thirdInput";

		// check hash generator generates same value for same inputs
		assertEquals(
				HashGenerator.getMD5(firstInput + secondInput + thirdInput),
				HashGenerator.getMD5(firstInput + secondInput + thirdInput));

	}

}
