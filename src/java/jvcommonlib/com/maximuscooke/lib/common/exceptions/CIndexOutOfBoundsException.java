package com.maximuscooke.lib.common.exceptions;

public class CIndexOutOfBoundsException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5642083355543886449L;

	public CIndexOutOfBoundsException()
	{
	}

	public CIndexOutOfBoundsException(String message)
	{
		super(message);
	}

	public CIndexOutOfBoundsException(Throwable cause)
	{
		super(cause);
	}

	public CIndexOutOfBoundsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CIndexOutOfBoundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
