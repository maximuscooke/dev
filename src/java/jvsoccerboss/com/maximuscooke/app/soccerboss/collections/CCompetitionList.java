package com.maximuscooke.app.soccerboss.collections;

import java.util.Collection;

import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CCountry;

public class CCompetitionList extends CList<CCompetition>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8028669101553130067L;

	public CCompetitionList()
	{
	}

	public CCompetitionList(int capacity)
	{
		super(capacity);
	}

	public CCompetitionList(Collection<? extends CCompetition> c)
	{
		super(c);
	}

	public final CCompetitionList getCompetitionsForCountry(CCountry country)
	{
		return getCompetitionList(this, country);
	}

	public final CCompetitionList getCompetitionList(CCountry country, int type)
	{
		return getCompetitionList(this, country, type);
	}

	public static CCompetitionList getCompetitionList(Collection<CCompetition> list, CCountry country)
	{
		return getCompetitionList(list, country, -1);
	}

	public static CCompetitionList getCompetitionList(Collection<CCompetition> list, CCountry country, int type)
	{
		CCompetitionList result = new CCompetitionList();

		if (type < 0)
		{
			for (CCompetition comp : list)
			{
				if (comp.getCountryID() == country.getIntegerID())
				{
					result.add(comp);
				}
			}
		} else
		{
			for (CCompetition comp : list)
			{
				if (comp.getCountryID() == country.getIntegerID() && comp.getType() == type)
				{
					result.add(comp);
				}
			}
		}

		return result;
	}
}
