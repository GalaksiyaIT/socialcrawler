package com.galaksiya.social.exception;

public class LinkedinAuthenticationException extends Exception {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 2750217769069911920L;

	public LinkedinAuthenticationException() {
		super();
	}

	public LinkedinAuthenticationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LinkedinAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkedinAuthenticationException(String message) {
		super(message);
	}

	public LinkedinAuthenticationException(Throwable cause) {
		super(cause);
	}

}
