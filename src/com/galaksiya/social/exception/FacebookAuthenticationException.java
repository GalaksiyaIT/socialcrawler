package com.galaksiya.social.exception;

public class FacebookAuthenticationException extends Exception {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 6559288809650296024L;

	public FacebookAuthenticationException() {
		super();
	}

	public FacebookAuthenticationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FacebookAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FacebookAuthenticationException(String message) {
		super(message);
	}

	public FacebookAuthenticationException(Throwable cause) {
		super(cause);
	}

}
