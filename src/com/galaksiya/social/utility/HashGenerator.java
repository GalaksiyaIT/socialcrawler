package com.galaksiya.social.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class HashGenerator {

	/**
	 * this method generates a hash value using given list of objects
	 * 
	 * @param data
	 *            object values that the hash code value will be generated from
	 * @return hash code value
	 */
	public static <T> int hashCode(Object... data) {
		int result = 0;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null) {
					result ^= data[i].hashCode();
					result = Integer.rotateRight(result, 1);
				}
			}
		}
		return Math.abs(result);
	}

	/**
	 * this method generates md5 hash from given input
	 * 
	 * @param input
	 * @return md5 hash value
	 */
	public static String getMD5(String input) {
		byte[] source;
		try {
			// Get byte according by specified coding.
			source = input.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			source = input.getBytes();
		}
		String result = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			// The result should be one 128 integer
			byte temp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = temp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			result = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
