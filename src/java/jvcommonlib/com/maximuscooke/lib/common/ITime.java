package com.maximuscooke.lib.common;

import java.time.LocalTime;

public interface ITime
{
	/**
	 * 
	 * @return current hour of day value
	 */
	int getHourOfDay();

	/**
	 * Set current hour value
	 * 
	 * @param hour hour value parameter
	 */
	void setHourOfDay(int hour);

	/**
	 * 
	 * @return current minute value
	 */
	int getMinutes();

	/**
	 * Set current minute
	 * 
	 * @param minute minute value parameter
	 */
	void setMinutes(int minute);

	/**
	 * 
	 * @return current second value
	 */
	int getSeconds();

	/**
	 * Set current second value
	 * 
	 * @param second second value
	 */
	void setSeconds(int second);
	
	public static LocalTime getLocalTime()
	{
		return LocalTime.now();
	}
	
	public static LocalTime getLocalTime(int hour, int minutes, int seconds)
	{
		return getLocalTime(hour, minutes, seconds, 0);
	}
	
	public static LocalTime getLocalTime(int hour, int minutes, int seconds, int nanosecs)
	{
		return LocalTime.of(hour, minutes, seconds, nanosecs);
	}
	
	public static LocalTime getLocalTime(ITime tm)
	{
		return getLocalTime(tm.getHourOfDay(), tm.getMinutes(), tm.getSeconds());
	}
}
