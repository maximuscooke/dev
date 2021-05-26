package com.maximuscooke.lib.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.maximuscooke.lib.common.collections.CArrayList;

public class CDateTime implements IDateTime, ISerializable
{
	private static final long serialVersionUID = -6420021886181092850L;

	public static final int DISPLAY_YEAR_MASK = (1 << 0);
	public static final int DISPLAY_MONTH_MASK = (1 << 1);
	public static final int DISPLAY_DAY_OF_MONTH_MASK = (1 << 2);
	public static final int DISPLAY_HOUR_OF_DAY_MASK = (1 << 3);
	public static final int DISPLAY_MINUTE_MASK = (1 << 4);
	public static final int DISPLAY_SECOND_MASK = (1 << 5);

	public static final int DISPLAY_ALL_MASK = (DISPLAY_YEAR_MASK | DISPLAY_MONTH_MASK | DISPLAY_DAY_OF_MONTH_MASK | DISPLAY_HOUR_OF_DAY_MASK | DISPLAY_MINUTE_MASK | DISPLAY_SECOND_MASK);

	public static final int MAX_MONTH = 12;
	public static final int MAX_HOUR = 24;
	public static final int MAX_MINUTE = 60;
	public static final int MAX_SECOND = 60;
	public static final int MAX_MILLISECOND = 1000;

	public static final int MAX_DAYS_IN_WEEK = 7;
	public static final int MAX_DAYS_IN_YEAR = 366;
	public static final int DATE_OFFSET = 1900;

	private int mYear = 0;
	private int mMonth = 0;
	private int mDayOfMonth = 0;
	private int mHourOfDay = 0;
	private int mMinute = 0;
	private int mSecond = 0;

	public CDateTime()
	{
	}

	public CDateTime(int year, int month, int dayOfMonth)
	{
		this(year, month, dayOfMonth, 0, 0);
	}

	public CDateTime(int year, int month, int dayOfMonth, int hourOfDay)
	{
		this(year, month, dayOfMonth, hourOfDay, 0, 0);
	}

	public CDateTime(int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{
		this(year, month, dayOfMonth, hourOfDay, minute, 0);
	}

	public CDateTime(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
	{

		this.setYear(year);

		this.setMonth(month);

		this.setDayOfMonth(dayOfMonth);

		this.setHourOfDay(hourOfDay);

		this.setMinutes(minute);

		this.setSeconds(second);
	}

	public CDateTime(IDateTime dt)
	{
		this(dt.getYear(), dt.getMonth(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinutes(), dt.getSeconds());
	}

	/**
	 * Return a IDateTime object as a GregorianCalendar object
	 * 
	 * @param d IDateTime object to convert
	 * @return GregorianCalendar object
	 */
	public static GregorianCalendar toGregorianCalendar(IDateTime d)
	{
		return new GregorianCalendar(d.getYear(), d.getMonth(), d.getDayOfMonth(), d.getHourOfDay(), d.getMinutes(), d.getSeconds());
	}

	/**
	 * Return standard date formatting string
	 * 
	 * @return String object
	 */
	public static String getDateString()
	{
		return getDateTimeString("dd/MMM/yyyy");
	}

	/**
	 * Return standard time formatting string
	 * 
	 * @return String object
	 */
	public static String getTimeString()
	{
		return getDateTimeString("HH:mm:ss");
	}

	/**
	 * Returns current date and time formatted as string
	 * 
	 * @param format either date or time formatting string
	 * @return current date and time formatted as string
	 */
	public static String getDateTimeString(String format)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(new Date());
	}

	/**
	 * Formats a IDateTime object as string
	 * 
	 * @param dt IDateTime to be converted
	 * @return IDateTime formatted as String
	 */
	public static String toTimeString(IDateTime dt)
	{
		return CDateTime.toTimeString(dt, ":");
	}

	/**
	 * Formats a IDateTime object as string
	 * 
	 * @param dt        IDateTime to be converted
	 * @param separator string separator
	 * @return IDateTime formatted as String
	 */
	public static String toTimeString(IDateTime dt, String separator)
	{
		return CDateTime.toTimeString(dt.getHourOfDay(), dt.getMinutes(), dt.getSeconds(), separator);
	}

	/**
	 * Formats time to a String
	 * 
	 * @param hourOfDay hour of day to convert
	 * @param minute    minute to convert
	 * @param second    second to convert
	 * @param separator string separator
	 * @return formatted String
	 */
	public static String toTimeString(int hourOfDay, int minute, int second, String separator)
	{
		StringBuilder sb = new StringBuilder(32);

		sb.append(String.format("%02d", hourOfDay));

		sb.append(separator);

		sb.append(String.format("%02d", minute));

		sb.append(separator);

		sb.append(String.format("%02d", second));

		return sb.toString();
	}

	/**
	 * Returns a random month of the year
	 * 
	 * @return random month as integer (e.g Calendar.JANUARY)
	 */
	public static int getRandomMonth()
	{
		return CApplication.getRandomInteger(MAX_MONTH);
	}

	/**
	 * Returns a random day of the month for a given month
	 * 
	 * @param month given month
	 * @return integer value of day of month
	 */
	public static int getRandomDayForMonth(int month)
	{
		int maxDay = 31;

		if (month == CGregorianCalendar.FEBRUARY)
		{
			maxDay = 28;
		} 
		else if (month == CGregorianCalendar.APRIL || month == CGregorianCalendar.JUNE || month == CGregorianCalendar.SEPTEMBER || month == CGregorianCalendar.NOVEMBER)
		{
			maxDay = 30;
		}

		return CApplication.getRandomInteger(1, maxDay + 1);
	}

	public static int getRandomYear(int minYear, int maxYear)
	{
		return CApplication.getRandomInteger(Math.min(minYear, maxYear), Math.max(minYear, maxYear));
	}

	public static IDateTime getRandomDate(int minYear, int maxYear)
	{
		int month = getRandomMonth();

		int dayOfMonth = getRandomDayForMonth(month);

		int year = getRandomYear(minYear, maxYear);

		return newInstance(year, month, dayOfMonth, 0, 0, 0);
	}

	/**
	 * Compares two dates and returns the minimum of the two
	 * 
	 * @param d1 first date for comparison
	 * @param d2 second date for comparison
	 * @return return the minimum of the two dates
	 */
	public static IDateTime getMin(IDateTime d1, IDateTime d2)
	{
		if (d1.compareToDateTime(d2) < 0)
		{
			return d1;
		}

		return d2;
	}

	/**
	 * Compares two dates and returns the maximum of the two
	 * 
	 * @param d1 first date for comparison
	 * @param d2 second date for comparison
	 * @return return the maximum of the two dates
	 */
	public static IDateTime getMax(IDateTime d1, IDateTime d2)
	{
		if (d1.compareToDateTime(d2) > 0)
		{
			return d1;
		}

		return d2;
	}

	public static IDateTime getRandomDateAndTime(int minYear, int maxYear)
	{

		int month = getRandomMonth();

		int dayOfMonth = getRandomDayForMonth(month);

		int year = getRandomYear(minYear, maxYear);

		int hour = getRandomHour();

		int minute = getRandomMinute();

		int second = getRandomSecond();

		return newInstance(year, month, dayOfMonth, hour, minute, second);
	}

	public static CDateTime newInstance(int year, int month, int dayOfMonth, int hour, int minute, int second)
	{
		return new CDateTime(year, month, dayOfMonth, hour, minute, second);
	}

	public static int getRandomHour()
	{
		return CApplication.getRandomInteger(MAX_HOUR);
	}

	public static int getRandomMinute()
	{
		return CApplication.getRandomInteger(MAX_MINUTE);
	}

	public static int getRandomSecond()
	{
		return CApplication.getRandomInteger(MAX_SECOND);
	}

	public static int getRandomMillisecond()
	{
		return CApplication.getRandomInteger(MAX_MILLISECOND);
	}

	public static int getCurrentYear()
	{
		return GregorianCalendar.getInstance(Locale.getDefault()).get(Calendar.YEAR);
	}

	public static String toDateString(GregorianCalendar cal)
	{
		return CDateTime.toDateString(cal, Calendar.LONG);
	}

	public static String toDateString(GregorianCalendar cal, int style)
	{
		return CDateTime.toDateString(cal, style, " ");
	}

	public static String toDateString(GregorianCalendar cal, int style, String separator)
	{
		StringBuilder sb = new StringBuilder(256);

		sb.append(cal.getDisplayName(Calendar.MONTH, style, Locale.getDefault()));

		sb.append(separator);

		sb.append(cal.get(Calendar.DAY_OF_MONTH));

		sb.append(separator);

		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}

	/**
	 * Return a list of all date which occur in a given year and month
	 * 
	 * @param year  specific year
	 * @param month specific month
	 * @param dates list of dates to check
	 * @return a list of all dates in specific year and month
	 */
	public static CArrayList<IDateTime> getDatesIn(int year, int month, final CArrayList<IDateTime> dates)
	{
		CArrayList<IDateTime> results = new CArrayList<IDateTime>();

		for (IDateTime dt : dates)
		{
			if (dt.getYear() == year && dt.getMonth() == month && results.contains(dt) == false)
			{
				results.add(dt);
			}
		}

		return results;
	}

	public static CArrayList<CArrayList<IDateTime>> splitByMonth(final CArrayList<IDateTime> dates)
	{
		CArrayList<CArrayList<IDateTime>> results = new CArrayList<CArrayList<IDateTime>>();

		if (dates != null)
		{
			CArrayList<IDateTime> copyDates = new CArrayList<IDateTime>(dates);

			copyDates.sort(CCollections.mDefaultSortDateTime);

			int yearStart = -1;

			int monthStart = -1;

			for (int i = 0; i < copyDates.size(); i++)
			{
				IDateTime dt = copyDates.getAt(i);

				if (yearStart != dt.getYear() || monthStart != dt.getMonth())
				{
					yearStart = dt.getYear();

					monthStart = dt.getMonth();

					CArrayList<IDateTime> datesInYearMonth = getDatesIn(yearStart, monthStart, copyDates);

					results.add(datesInYearMonth);
				}
			}
		}

		return results;
	}

	public static int compareToTime(IDateTime dt1, IDateTime dt2)
	{
		return CCollections.mDefaultSortTime.compare(dt1, dt2);
	}

	public static int compareToDate(IDateTime dt1, IDateTime dt2)
	{
		return CCollections.mDefaultSortDate.compare(dt1, dt2);
	}

	public static int compareToDateTime(IDateTime dt1, IDateTime dt2)
	{
		return CCollections.mDefaultSortDateTime.compare(dt1, dt2);
	}

	@Override
	public int compareToDateTime(IDateTime d)
	{
		return CDateTime.compareToDateTime(this, d);
	}

	@Override
	public final int getYear()
	{
		return this.mYear;
	}

	@Override
	public final void setYear(int year)
	{
		this.mYear = year;
	}

	@Override
	public final int getMonth()
	{
		return this.mMonth;
	}

	@Override
	public final void setMonth(int month)
	{
		this.mMonth = month;
	}

	@Override
	public final int getDayOfMonth()
	{
		return this.mDayOfMonth;
	}

	@Override
	public final void setDayOfMonth(int dayOfMonth)
	{
		this.mDayOfMonth = dayOfMonth;
	}

	@Override
	public final int getHourOfDay()
	{
		return this.mHourOfDay;
	}

	@Override
	public final void setHourOfDay(int hour)
	{
		this.mHourOfDay = hour;
	}

	@Override
	public final void setMinutes(int minute)
	{
		this.mMinute = minute;
	}

	@Override
	public final int getMinutes()
	{
		return this.mMinute;
	}

	@Override
	public final int getSeconds()
	{
		return this.mSecond;
	}

	@Override
	public final void setSeconds(int second)
	{
		this.mSecond = second;
	}

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
	public boolean equals(Object o)
	{
		IDateTime dt = (o instanceof IDateTime) ? (IDateTime) o : null;

		if (dt != null)
		{
			return (this.compareToDateTime(dt) == CCollections.EQUAL);
		}

		return false;
	}

	public final String toDisplayString(int style)
	{

		return String.format("%04d:%02d:%02d:%02d:%02d:%02d", this.getYear(), this.getMonth(), this.getDayOfMonth(), this.getHourOfDay(), this.getMinutes(), this.getSeconds());
	}

	public static int getAge(IDateTime dateOfBirth, IDateTime currentDate)
	{

		IDateTime now = currentDate;

		int year1 = now.getYear();

		int year2 = dateOfBirth.getYear();

		int age = year1 - year2;

		int month1 = now.getMonth();

		int month2 = dateOfBirth.getMonth();

		if (month2 > month1)
		{
			age--;
		} 
		else if (month1 == month2)
		{

			int day1 = now.getDayOfMonth();

			int day2 = dateOfBirth.getDayOfMonth();

			if (day2 > day1)
			{
				age--;
			}
		}

		return age;
	}

	@Override
	public String toString()
	{
		return toDisplayString(IDisplayString.STYLE_LONG);
	}
}
