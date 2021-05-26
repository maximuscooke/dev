package com.maximuscooke.lib.common.exceptions;

public class CNetException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4997770663080613857L;

	public CNetException()
	{
	}

	public CNetException(String message)
	{
		super(message);
	}

	public CNetException(Throwable cause)
	{
		super(cause);
	}

	public CNetException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CNetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}