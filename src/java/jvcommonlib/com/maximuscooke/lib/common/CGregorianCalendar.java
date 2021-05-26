package com.maximuscooke.lib.common;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CGregorianCalendar extends GregorianCalendar implements IDateTime, IDisplayString
{
	private static final long serialVersionUID = 959757705828905095L;

	public static final int DISPLAY_STYLE_SHORT 			= 10;
	public static final int DISPLAY_STYLE_LONG 			= 20;
	public static final int DISPLAY_STYLE_SHORT_TIME 	= 30;
	public static final int DISPLAY_STYLE_SHORT_DATE 	= 40;
	public static final int DISPLAY_STYLE_EXTRA_LONG 	= 50;
	

	private static String mDefaultFormatString = "EEE, d MMM yyyy HH:mm:ss";

	public CGregorianCalendar()
	{
	}

	public CGregorianCalendar(TimeZone zone)
	{
		super(zone);
	}

	public CGregorianCalendar(Locale aLocale)
	{
		super(aLocale);
	}

	public CGregorianCalendar(TimeZone zone, Locale aLocale)
	{
		super(zone, aLocale);
	}

	public CGregorianCalendar(int year, int month, int dayOfMonth)
	{
		super(year, month, dayOfMonth);
	}

	public CGregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{
		super(year, month, dayOfMonth, hourOfDay, minute);
	}

	public CGregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
	{
		super(year, month, dayOfMonth, hourOfDay, minute, second);
	}

	public CGregorianCalendar(IDateTime dt)
	{
		super(dt.getYear(), dt.getMonth(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinutes(), dt.getSeconds());
	}
		
	public static void setDefaultDisplayFormat()
	{
		setDisplayFormat("EEE, d MMM yyyy HH:mm:ss");
	}
	
	public static CGregorianCalendar toGregorianCalendar(Date d)
	{
		CGregorianCalendar gc = new CGregorianCalendar();

		gc.setTime( d );

		return gc;
	}
	
	public static Date toDate(CGregorianCalendar dt)
	{
		return toDate(dt, CDate.DATE_OFFSET);
	}
	
	@SuppressWarnings("deprecation")
	public static Date toDate(CGregorianCalendar dt, int yearOffset)
	{
		return new Date((dt.getYear() - yearOffset), dt.getMonth(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinutes(), dt.getSeconds());
	}
	
	public static void setDisplayFormat(String pattern)
	{
		mDefaultFormatString = pattern;
	}
	
	public static long getDifferenceInHours(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.HOURS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInDays(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.DAYS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInMinutes(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.MINUTES.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInSeconds(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.SECONDS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInYears(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.YEARS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInMilli(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.MILLIS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static long getDifferenceInMicro(CGregorianCalendar dt1, CGregorianCalendar dt2)
	{
		return ChronoUnit.MICROS.between(dt1.toInstant(), dt2.toInstant());
	}
	
	public static String toExportString(CGregorianCalendar dt, String separator)
	{
		return String.format("%d%s%d%s%d%s%d%s%d%s%d", 	dt.getYear(),
																			separator,
																			dt.getMonth(), 
																			separator,
																			dt.getDayOfMonth(),
																			separator,
																			dt.getHourOfDay(),
																			separator,
																			dt.getMinutes(),
																			separator,
																			dt.getSeconds());
	}
	
	public static CGregorianCalendar parseExportString(List<String> list, int startIndex)
	{
		return new CGregorianCalendar(Integer.parseInt(list.get(startIndex++)),
												Integer.parseInt(list.get(startIndex++)),
												Integer.parseInt(list.get(startIndex++)),
												Integer.parseInt(list.get(startIndex++)),
												Integer.parseInt(list.get(startIndex++)),
												Integer.parseInt(list.get(startIndex++)));
	}

	public static String toString(GregorianCalendar gc)
	{
		return toString(gc, mDefaultFormatString);
	}
	
	public static String toString(GregorianCalendar gc, String pattern)
	{
		return new SimpleDateFormat(pattern).format(gc.getTime());
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
	public String toDisplayString(int style)
	{
		if (style == CGregorianCalendar.DISPLAY_STYLE_SHORT)
		{
			return toString(this, "yyyy:MM:dd hh:mm:ss");
		}
		
		if (style == CGregorianCalendar.DISPLAY_STYLE_SHORT_TIME)
		{
			return toString(this, "hh:mm:ss");
		}
		
		if (style == CGregorianCalendar.DISPLAY_STYLE_SHORT_DATE)
		{
			return toString(this, "yyyy:MM:dd");
		}
		
		if (style == CGregorianCalendar.DISPLAY_STYLE_EXTRA_LONG)
		{
			return toString(this, "EEE, d MMM yyyy HH:mm:ss zzzz");
		}
		
		return CGregorianCalendar.toString(this);
	}
	
	@Override
	public String toString()
	{
		return CGregorianCalendar.toString(this);
	}

	@Override
	public int getHourOfDay()
	{
		return this.get(CGregorianCalendar.HOUR_OF_DAY);
	}

	@Override
	public void setHourOfDay(int hour)
	{
		this.set(CGregorianCalendar.HOUR_OF_DAY, hour);
	}

	@Override
	public int getMinutes()
	{
		return this.get(CGregorianCalendar.MINUTE);
	}

	@Override
	public void setMinutes(int minute)
	{
		this.set(CGregorianCalendar.MINUTE, minute);
	}

	@Override
	public int getSeconds()
	{
		return get(CGregorianCalendar.SECOND);
	}

	@Override
	public void setSeconds(int second)
	{
		this.set(CGregorianCalendar.SECOND, second);
	}

	@Override
	public int getYear()
	{
		return this.get(CGregorianCalendar.YEAR);
	}

	@Override
	public void setYear(int year)
	{
		this.set(CGregorianCalendar.YEAR, year);
	}

	@Override
	public int getMonth()
	{
		return this.get(CGregorianCalendar.MONTH);
	}

	@Override
	public void setMonth(int month)
	{
		this.set(CGregorianCalendar.MONTH, month);
	}

	@Override
	public int getDayOfMonth()
	{
		return this.get(CGregorianCalendar.DAY_OF_MONTH);
	}

	@Override
	public void setDayOfMonth(int dayOfMonth)
	{
		this.set(CGregorianCalendar.DAY_OF_MONTH, dayOfMonth);
	}

	@Override
	public int compareToDateTime(IDateTime dt)
	{
		return CCollections.mDefaultSortDateTime.compare(this, dt);
	}

}
