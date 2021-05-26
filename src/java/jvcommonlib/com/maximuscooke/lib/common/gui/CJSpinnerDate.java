package com.maximuscooke.lib.common.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import com.maximuscooke.lib.common.CDate;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.IDateTime;

@SuppressWarnings("serial")
public class CJSpinnerDate extends CJSpinnerEx
{
	private SpinnerDateModel mModel = null;

	public CJSpinnerDate()
	{
		this(new SpinnerDateModel());
	}

	public CJSpinnerDate(SpinnerDateModel model)
	{
		super(model);

		this.mModel = model;
	}
	
	public CJSpinnerDate(Date value, Date start, Date end, int calendarField, String format)
	{
		this( new SpinnerDateModel(value, start, end, calendarField) );
		
		this.setDisplayFormat(format);
	}
	
	public CJSpinnerDate(String format)
	{
		this(new SpinnerDateModel());
		
		this.setDisplayFormat(format);
	}

	public void setDisplayFormat(String format)
	{
		// Example "dd.MMM.yy"
		SimpleDateFormat formatmodel = new SimpleDateFormat(format);
		
		this.setEditor(new JSpinner.DateEditor(this, formatmodel.toPattern()));
		
	}
	
	public final SpinnerDateModel getDateModel()
	{
		return this.mModel;
	}
	
	public final Date getDate()
	{
		return this.mModel.getDate();
	}
	
	public final void setDateRange(IDateTime start, IDateTime end)
	{
		this.setDateMin(start);
		
		this.setDateMax(end);
	}
	
	@SuppressWarnings("deprecation")
	public final void setDateMin(IDateTime d)
	{
		this.setDateMin( new Date(d.getYear() - CDate.DATE_OFFSET, d.getMonth(), d.getDayOfMonth(), d.getHourOfDay(), d.getMinutes(), d.getSeconds()) );
	}
	
	public final void setDateMin(Date d)
	{
		this.mModel.setStart(d);
	}
	
	public final CGregorianCalendar getDateMin()
	{
		return CGregorianCalendar.toGregorianCalendar( (Date)this.mModel.getStart() );
	}
	
	@SuppressWarnings("deprecation")
	public final void setDateMax(IDateTime d)
	{
		this.setDateMax( new Date(d.getYear() - CDate.DATE_OFFSET, d.getMonth(), d.getDayOfMonth(), d.getHourOfDay(), d.getMinutes(), d.getSeconds()) );
	}
	
	public final void setDateMax(Date d)
	{
		this.mModel.setEnd(d);
	}
	
	public final CGregorianCalendar getDateMax()
	{
		return CGregorianCalendar.toGregorianCalendar( (Date)this.mModel.getEnd() );
	}
	
	@SuppressWarnings("deprecation")
	public final void setDateValue(IDateTime d)
	{
		this.setDateValue( new Date(d.getYear() - CDate.DATE_OFFSET, d.getMonth(), d.getDayOfMonth(), d.getHourOfDay(), d.getMinutes(), d.getSeconds()) );
	}
	
	public final void setDateValue(Date d)
	{
		this.mModel.setValue(d);		
	}
	
	public final CGregorianCalendar getDateValue()
	{
		return CGregorianCalendar.toGregorianCalendar( this.getDate() );
	}

}
