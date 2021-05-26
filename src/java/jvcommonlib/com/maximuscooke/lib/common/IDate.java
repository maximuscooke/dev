package com.maximuscooke.lib.common;

import java.time.LocalDate;
import java.time.Month;

public interface IDate
{
	/**
	 * 
	 * @return current year value
	 */
	int getYear();

	/**
	 * Set current year value
	 * 
	 * @param year year value
	 */
	void setYear(int year);

	/**
	 * 
	 * @return current month value
	 */
	int getMonth();

	/**
	 * Set current month value
	 * 
	 * @param month month value
	 */
	void setMonth(int month);

	/**
	 * 
	 * @return current day of month
	 */
	int getDayOfMonth();

	/**
	 * Set current day of month value
	 * 
	 * @param dayOfMonth day of month value
	 */
	void setDayOfMonth(int dayOfMonth);
	
	public static LocalDate getLocalDate()
	{
		return LocalDate.now();
	}
	
	public static LocalDate getLocalDate(int year, int month, int dayOfMonth)
	{
		return LocalDate.of(year, month, dayOfMonth);
	}
	
	public static LocalDate getLocalDate(int year, Month month, int dayOfMonth)
	{
		return LocalDate.of(year, month, dayOfMonth);
	}

	public static LocalDate getLocalDate(IDate dt)
	{
		return LocalDate.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth());
	}
}
