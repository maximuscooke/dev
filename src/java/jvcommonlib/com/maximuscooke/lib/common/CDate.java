package com.maximuscooke.lib.common;

import java.util.Date;

public class CDate extends Date implements IDateTime
{
	private static final long serialVersionUID = -959554652339621677L;
	
	public static final int DATE_OFFSET	= 1900;

	public CDate()
	{
	}

	public CDate(long date)
	{
		super(date);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public final int getHourOfDay()
	{
		return this.getHours();
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void setHourOfDay(int hour)
	{
		this.setHours(hour);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final int getDayOfMonth()
	{
		return this.getDate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void setDayOfMonth(int dayOfMonth)
	{
		this.setDate(dayOfMonth);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(IDateTime dt)
	{
		this.setYear(dt.getYear());
		
		this.setMonth(dt.getMonth());
		
		this.setDayOfMonth(dt.getDayOfMonth());
		
		this.setHourOfDay(dt.getHourOfDay());
		
		this.setMinutes(dt.getMinutes());
		
		this.setSeconds(dt.getSeconds());
	}

	@Override
	public int compareToDateTime(IDateTime dt)
	{		
		return CCollections.mDefaultSortDateTime.compare(this, dt);
	}
}
