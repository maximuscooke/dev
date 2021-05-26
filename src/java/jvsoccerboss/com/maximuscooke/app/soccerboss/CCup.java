package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.collections.CArrayList;

public abstract class CCup extends CCompetition
{
	private static final long serialVersionUID = 3572722063271554003L;
	
	private CArrayList<CGregorianCalendar> mCupRoundDates = new CArrayList<CGregorianCalendar>();

	public CCup()
	{
	}

	public CCup(String name, int countryId, int id, int maxClubs)
	{
		super(name, countryId, id, maxClubs);
	}

	public final CArrayList<CGregorianCalendar> getCupRoundDates()
	{
		return this.mCupRoundDates;
	}

	public final void setCupRoundDates(CArrayList<CGregorianCalendar> dates)
	{
		this.mCupRoundDates = dates;
	}
}
