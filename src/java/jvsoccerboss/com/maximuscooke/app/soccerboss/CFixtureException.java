package com.maximuscooke.app.soccerboss;

public class CFixtureException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4412046432914004468L;

	public static final int GENERAL_EXCEPTION = 0;
	public static final int FIXTURE_CLASH_EXCEPTION = 10;
	public static final int FIXTURE_ALREADY_SCHEDULED_EXCEPTION = 20;
	public static final int FIXTURE_NULL_DATE_EXCEPTION = 30;
	public static final int FIXTURE_FXT_GAP_EXCEPTION = 40;
	
	private int mType = GENERAL_EXCEPTION;
	
	public CFixtureException(String message, int exceptionType)
	{
		super(message);
		
		this.mType = exceptionType;
	}

	public final int getType()
	{
		return mType;
	}
}
