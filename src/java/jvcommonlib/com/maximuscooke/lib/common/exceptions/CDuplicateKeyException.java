package com.maximuscooke.lib.common.exceptions;

public class CDuplicateKeyException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4997770663080613857L;

	public CDuplicateKeyException()
	{
	}

	public CDuplicateKeyException(String message)
	{
		super(message);
	}

	public CDuplicateKeyException(Throwable cause)
	{
		super(cause);
	}

	public CDuplicateKeyException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CDuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
