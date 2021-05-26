package com.maximuscooke.lib.common.calendar;
import java.util.Iterator;

import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.ICalendarEntry;
import com.maximuscooke.lib.common.IDate;
import com.maximuscooke.lib.common.IDateTimeStamp;
import com.maximuscooke.lib.common.ISerializable;
import com.maximuscooke.lib.common.IStringID;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CCalendarDay implements IStringID, ISerializable, IDateTimeStamp, Iterable<ICalendarEntry>
{
	private static final long serialVersionUID = -3424319317226268775L;
	
	private CArrayList<ICalendarEntry> mEntries = new CArrayList<ICalendarEntry>();
	
	private String mStringID=null;
	
	private CGregorianCalendar mDate=null;
	
	public CCalendarDay()
	{
	}

	public CCalendarDay(CGregorianCalendar dt)
	{
		this.mStringID = toKeyString(dt);
		
		this.setDateTime(dt);
	}
	
	public static String toKeyString(IDate d)
	{
		return toKeyString( d.getYear(), d.getMonth(), d.getDayOfMonth() );
	}
	
	public static String toKeyString(int year, int month, int dayOfMonth)
	{
		return String.format("%04d:%02d:%02d", year, month, dayOfMonth);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("[%02d:%02d:%04d]", this.getDayOfMonth(), this.getMonth(), this.getYear()));
		
		if (this.mEntries.size() > 0)
		{
			CCollections.sortByDateTimeStamp(this.mEntries);
			
			int i;
			for(i = 0; i < this.mEntries.size() - 1; i++)
			{
				sb.append(" ");
				
				ICalendarEntry e = this.mEntries.getAt(i);
				
				sb.append(String.format("[%02d:%02d:%02d] %s,", e.getDateTime().getHourOfDay(), e.getDateTime().getMinutes(), e.getDateTime().getSeconds(), e.getName() ) );
			}
			
			sb.append(" ");
			
			ICalendarEntry e = this.mEntries.getAt(i);
			
			sb.append(String.format("[%02d:%02d:%02d] %s", e.getDateTime().getHourOfDay(), e.getDateTime().getMinutes(), e.getDateTime().getSeconds(), e.getName() ) );
		}
		
		return sb.toString();
	}
	
	public final void clear()
	{
		this.mEntries.clear();
	}
	
	public String toKeyString()
	{
		return CCalendarDay.toKeyString(this.mDate);
	}
	
	public final void addEntry(ICalendarEntry e)
	{
		this.addEntry(e, 0);
	}
	
	public final void addEntry(ICalendarEntry e, int options)
	{
		this.mEntries.add(e);
	}
	
	public boolean removeEntry(ICalendarEntry e)
	{
		return this.mEntries.remove(e);
	}

	public int getYear()
	{
		return this.mDate.getYear();
	}

	public int getMonth()
	{
		return this.mDate.getMonth();
	}

	public int getDayOfMonth()
	{
		return this.mDate.getDayOfMonth();
	}
	
	public final CArrayList<ICalendarEntry> getEntries()
	{
		return this.mEntries;
	}

	public final void setEntries(CArrayList<ICalendarEntry> entries)
	{
		this.mEntries = entries;
	}

	@Override
	public final CGregorianCalendar getDateTime()
	{
		return mDate;
	}

	public final void setDateTime(CGregorianCalendar dt)
	{
		this.mDate = dt;
	}

	@Override
	public Iterator<ICalendarEntry> iterator()
	{
		return this.mEntries.iterator();
	}

	@Override
	public String getStringID()
	{
		return this.mStringID;
	}
}
