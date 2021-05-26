package com.maximuscooke.lib.common.exceptions;

public class CKeyNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4597731423929758607L;

	public CKeyNotFoundException()
	{
	}

	public CKeyNotFoundException(String message)
	{
		super(message);
	}

	public CKeyNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public CKeyNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CKeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
