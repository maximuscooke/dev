package com.maximuscooke.app.soccerboss.editor.schedule;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CGregorianCalendar;

public class CScheduleCell extends CTableCell
{
	private String mDateTimeString=null;
	
	private CGregorianCalendar mDateTime=null;
		
	public CScheduleCell(CGregorianCalendar dt, boolean bDateOnly)
	{
		if (dt != null)
		{
			this.mDateTime = new CGregorianCalendar(dt);

			this.setIsDateOnly(bDateOnly);

			if (bDateOnly)
			{
				this.mDateTimeString = CGregorianCalendar.toString(dt, "EEE dd MMM yyyy");				
			}
		}
	}

	public final CGregorianCalendar getDateTime()
	{
		return this.mDateTime;
	}

	@Override
	public String toString()
	{
		return (this.isDateOnly() || this.mDateTimeString != null) ? this.mDateTimeString : (this.isScheduled() ? this.getFixture().toString() : CApplication.EMPTY_STRING);
	}
}
