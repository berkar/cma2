package se.berkar.web.servlets;

public class IllegalFormatException extends IllegalArgumentException {
	public IllegalFormatException() {
	}

	public IllegalFormatException(String s) {
		super(s);
	}

	public IllegalFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalFormatException(Throwable cause) {
		super(cause);
	}
}
