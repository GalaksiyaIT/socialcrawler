package com.galaksiya.social.exception;

public class FacebookAccessExpirationException extends FacebookAuthenticationException {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -8382072502279039591L;

	public FacebookAccessExpirationException() {
		super();
	}

	public FacebookAccessExpirationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FacebookAccessExpirationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FacebookAccessExpirationException(String message) {
		super(message);
	}

	public FacebookAccessExpirationException(Throwable cause) {
		super(cause);
	}

}
