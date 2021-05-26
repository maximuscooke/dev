package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CGregorianCalendar;

@SuppressWarnings("serial")
public class CScheduleValidationException extends Exception
{
	private CFixture mFixture=null;
	private CGregorianCalendar mDateTime=null;
	private int mErrorCode=CFixture.FXT_STATE_VALID;
	private int mClubID=-1;
	
	public CScheduleValidationException()
	{
	}
		
	public CScheduleValidationException(CFixture f, int errorCode, int clubID, CGregorianCalendar date, String msg)
	{
		super(msg);
		
		this.setFixture(f);
		
		this.setDateTime(date);
		
		this.setClubID(clubID);
		
		this.setErrorCode(errorCode);
	}

	public final CFixture getFixture()
	{
		return this.mFixture;
	}

	public final void setFixture(CFixture mFixture)
	{
		this.mFixture = mFixture;
	}

	public final int getErrorCode()
	{
		return this.mErrorCode;
	}

	public final void setErrorCode(int errorCode)
	{
		this.mErrorCode = errorCode;
	}

	public final CGregorianCalendar getDateTime()
	{
		return this.mDateTime;
	}

	public final void setDateTime(CGregorianCalendar dt)
	{
		this.mDateTime = dt;
	}

	public final int getClubID()
	{
		return this.mClubID;
	}

	public final void setClubID(int id)
	{
		this.mClubID = id;
	}
}
