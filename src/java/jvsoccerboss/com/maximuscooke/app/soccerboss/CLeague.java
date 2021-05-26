package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.IPrecedenceOrder;

public abstract class CLeague extends CCompetition implements IPrecedenceOrder
{
	private static final long serialVersionUID = -4910346046561799634L;
	
	private CGregorianCalendar mStartDate = null;
	private CGregorianCalendar mEndDate = null;

	private int mPointsForWin = 0;
	private int mPointsForDraw = 0;
	private int mPointsForDefeat = 0;
	
	private int mPrecendenceOrder = 0;
	
	private int mPromotionCount=0;
	private int mRelegationCount=0;
	
	public CLeague()
	{
	}

	public CLeague(String name, int countryId, int id, int maxClubs, int precedenceOrder)
	{
		super(name, countryId, id, maxClubs);
		
		this.setPrecedenceOrder(precedenceOrder);
	}

	public final int getPointsForWin()
	{
		return this.mPointsForWin;
	}

	public final void setPointsForWin(int pts)
	{
		this.mPointsForWin = pts;
	}

	public final int getPointsForDraw()
	{
		return this.mPointsForDraw;
	}

	public final void setPointsForDraw(int pts)
	{
		this.mPointsForDraw = pts;
	}

	public final int getPointsForDefeat()
	{
		return this.mPointsForDefeat;
	}

	public final void setPointsForDefeat(int pts)
	{
		this.mPointsForDefeat = pts;
	}

	public final CGregorianCalendar getStartDate()
	{
		return this.mStartDate;
	}

	public final void setStartDate(CGregorianCalendar dt)
	{
		this.mStartDate = dt;
	}

	public final CGregorianCalendar getEndDate()
	{
		return this.mEndDate;
	}
	
	public final void setEndDate(CGregorianCalendar dt)
	{
		this.mEndDate = dt;
	}

	@Override
	public final int getPrecedenceOrder()
	{
		return this.mPrecendenceOrder;
	}

	@Override
	public final void setPrecedenceOrder(int order)
	{
		this.mPrecendenceOrder = order;
	}

	public final int getPromotionCount()
	{
		return this.mPromotionCount;
	}

	public final void setPromotionCount(int count)
	{
		this.mPromotionCount = count;
	}

	public final int getRelegationCount()
	{
		return this.mRelegationCount;
	}

	public final void setRelegationCount(int count)
	{
		this.mRelegationCount = count;
	}
}
