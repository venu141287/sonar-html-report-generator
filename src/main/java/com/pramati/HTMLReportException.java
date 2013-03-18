package com.pramati;

public class HTMLReportException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public HTMLReportException() { 
		super();
	}
	public HTMLReportException(String message) { 
		super(message); 
	}
	public HTMLReportException(String message, Throwable cause) { 
		super(message, cause);
	}
	public HTMLReportException(Throwable cause) { 
		super(cause);
	}

}
