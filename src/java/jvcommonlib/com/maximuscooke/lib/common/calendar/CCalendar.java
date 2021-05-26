package com.maximuscooke.lib.common.calendar;

import java.util.Iterator;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.ICalendarEntry;
import com.maximuscooke.lib.common.INotify;
import com.maximuscooke.lib.common.INotifyListener;
import com.maximuscooke.lib.common.INotifyMessage;
import com.maximuscooke.lib.common.ISerializable;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;

public class CCalendar implements ISerializable, INotify, Iterable<CCalendarDay>
{
	private static final long serialVersionUID = -6800206514977666111L;
	
	public static final byte SORT_ORDER_BIT = 0;
	public static final byte ENABLE_NOTIFICATIONS_BIT = 1;
	
	private CHashMap<String, CCalendarDay> mDays = new CHashMap<String, CCalendarDay>();
	
	private CArrayList<INotifyListener> mListeners = null;
	
	private int mOptions=0;
	
	public CCalendar()
	{
	}
	
	public ICalendarEntry scheduleEntry(ICalendarEntry e)
	{
		String key = CCalendarDay.toKeyString(e.getDateTime());
		
		CCalendarDay day = this.mDays.get(key);
		
		if (day == null)
		{
			day = new CCalendarDay( new CGregorianCalendar(e.getDateTime()) );
			
			this.mDays.add(key, day);
		}
		
		day.addEntry(e);
		
		return e;
	}
	
	public boolean removeEntry(ICalendarEntry e)
	{
		return removeEntry(e, 0);
	}
	
	public boolean removeEntry(ICalendarEntry e, int options)
	{
		String key = CCalendarDay.toKeyString(e.getDateTime());
		
		CCalendarDay day = this.mDays.get(key);
		
		if (day != null)
		{
			if (day.getEntries().size() <= 1)
			{
				this.mDays.remove(key);
			}
			
			boolean bResult = day.removeEntry(e);
			
			return bResult;
		}
		
		return false;
	}
	
	public CArrayList<ICalendarEntry> findEntries(String name)
	{
		CArrayList<ICalendarEntry> results = new CArrayList<ICalendarEntry>();
		
		this.mDays.values().forEach( day -> { day.getEntries().forEach( e -> { if (e.getName() != null && e.getName().equalsIgnoreCase(name) ) { results.add(e); } }); } );
		
		return results;
	}
	
	public CArrayList<ICalendarEntry> findEntries(int year, int month, int dayOfMonth)
	{
		CArrayList<ICalendarEntry> results = new CArrayList<ICalendarEntry>();
		
		this.mDays.values().forEach( day -> { if (day.getYear() == year && day.getMonth() == month && day.getDayOfMonth() == dayOfMonth) { day.getEntries().forEach( e -> results.add(e)); } } );
		
		return results;
	}
	
	public CArrayList<ICalendarEntry> findEntries(int year, int month)
	{
		CArrayList<ICalendarEntry> results = new CArrayList<ICalendarEntry>();
		
		this.mDays.values().forEach( day -> { if (day.getYear() == year && day.getMonth() == month) { day.getEntries().forEach( e -> results.add(e)); } } );
		
		return results;
	}

	public final void clear()
	{
		this.mDays.clear();
	}
	
	public final CArrayList<CCalendarDay> getDaysAsList()
	{
		CArrayList<CCalendarDay> days = new CArrayList<CCalendarDay>(this.mDays.values());
		
		CCollections.sortByDateTimeStamp(days);
		
		if (CApplication.isBitSet(CCalendar.SORT_ORDER_BIT, this.mOptions))
		{
			days.reverseOrder();
		}

		return days;
	}
	
	@Override
	public void register(INotifyListener listener)
	{
		if (this.mListeners == null)
		{
			this.mListeners = new CArrayList<INotifyListener>();
		}
		
		this.mListeners.add(listener);
	}
	
	@Override
	public void unregister(INotifyListener listener)
	{
		if (this.mListeners != null)
		{
			this.mListeners.remove(listener);
		}
	}
	
	@Override
	public void notify(int action, INotifyMessage msg)
	{
		if (this.mListeners != null && this.getEnableNotifications())
		{
			this.mListeners.forEach( l -> l.notifyListeners(this, action, msg) );
		}
	}

	@Override
	public String toString()
	{
		if (this.mDays.getSize() > 0)
		{
			CArrayList<CCalendarDay> days = getDaysAsList();
						
			StringBuilder sb = new StringBuilder();
			
			days.forEach( d -> { sb.append( d.toString() ); sb.append("\n"); } );
			
			return sb.toString();
		}
		else
		{
			return "Calendar is empty, no entries scheduled.";
		}
	}
	
	public final boolean getEnableNotifications()
	{
		return CApplication.isBitSet(CCalendar.ENABLE_NOTIFICATIONS_BIT, this.mOptions);		
	}
	
	public final void setEnableNotifications(boolean bEnable)
	{
		this.mOptions = bEnable ? CApplication.setBit(CCalendar.ENABLE_NOTIFICATIONS_BIT, this.mOptions) : CApplication.clearBit(CCalendar.ENABLE_NOTIFICATIONS_BIT, this.mOptions);
	}
	
	public final void setSortOrder(int order)
	{
		this.mOptions = (order == CCollections.SORT_DESCENDING) ? CApplication.setBit(CCalendar.SORT_ORDER_BIT, this.mOptions) : CApplication.clearBit(CCalendar.SORT_ORDER_BIT, this.mOptions);
	}

	public final int getOptions()
	{
		return this.mOptions;
	}

	public final void setOptions(int opt)
	{
		this.mOptions = opt;
	}

	public final CHashMap<String, CCalendarDay> getDays()
	{
		return this.mDays;
	}

	public final void setDays(CHashMap<String, CCalendarDay> days)
	{
		this.mDays = days;
	}

	@Override
	public Iterator<CCalendarDay> iterator()
	{
		return this.mDays.values().iterator();
	}
}
