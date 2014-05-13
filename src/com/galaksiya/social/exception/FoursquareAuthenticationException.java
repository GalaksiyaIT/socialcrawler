package com.galaksiya.social.exception;

public class FoursquareAuthenticationException extends Exception {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 5334972204137583284L;

	public FoursquareAuthenticationException() {
		super();
	}

	public FoursquareAuthenticationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FoursquareAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FoursquareAuthenticationException(String message) {
		super(message);
	}

	public FoursquareAuthenticationException(Throwable cause) {
		super(cause);
	}

}
