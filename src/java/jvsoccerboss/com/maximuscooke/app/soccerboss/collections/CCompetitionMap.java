package com.maximuscooke.app.soccerboss.collections;

import java.util.Map;

import com.maximuscooke.app.soccerboss.CCompetition;
import com.maximuscooke.app.soccerboss.CCountry;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CCompetitionMap extends CMap<Integer, CCompetition>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3062322448678294114L;

	public static final int ALL_TYPES = -1;

	public CCompetitionMap()
	{
	}

	public CCompetitionMap(int capacity)
	{
		super(capacity);
	}

	public CCompetitionMap(int capacity, float loadFactor)
	{
		super(capacity, loadFactor);
	}

	public CCompetitionMap(Map<Integer, ? extends CCompetition> m)
	{
		super(m);
	}

	/**
	 * Add Competition to map
	 * 
	 * @param CCountry object to add
	 */
	public int add(CCompetition c)
	{

		int id = this.getNextID();

		c.setIntegerID(id);

		this.add(id, c);

		return id;
	}

	/**
	 * Returns a list of competitions for a given country
	 * 
	 * @param country specific country
	 * @return list of competitions
	 */
	public CCompetitionList getCompetitionsForCountry(CCountry country)
	{
		return getCompetitionsForCountry(country.getIntegerID());
	}

	/**
	 * Returns a list of competitions for a given country
	 * 
	 * @param id specific country ID
	 * @return list of competitions
	 */
	public CCompetitionList getCompetitionsForCountry(int id)
	{

		CCompetitionList list = new CCompetitionList();

		for (CCompetition c : this.values())
		{

			if (c.getCountryID() == id)
			{
				list.add(c);
			}
		}

		return list;
	}

	public final CCompetitionMap getCompetitionMap(CArrayList<Integer> clubIds)
	{

		CCompetitionMap map = new CCompetitionMap(clubIds.getSize() + 1);

		for (int id : clubIds)
		{

			if (this.containsKey(id))
			{

				map.add(this.get(id));
			}
		}

		return map;
	}

	public final CCompetitionList getCompetitionList(CArrayList<Integer> compIds)
	{

		CCompetitionList list = new CCompetitionList(compIds.getSize() + 1);

		for (int id : compIds)
		{

			if (this.containsKey(id))
			{

				list.add(this.get(id));
			}
		}

		return list;
	}

}
