package com.galaksiya.social.utility;

public class HashGenerator {

	public static <T> int hashCode(String... data) {
		int result = 0;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				result ^= data[i].hashCode();
				result = Integer.rotateRight(result, 1);
			}
		}
		return Math.abs(result);
	}

}
