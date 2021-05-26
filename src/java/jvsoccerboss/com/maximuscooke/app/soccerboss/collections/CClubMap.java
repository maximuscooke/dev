package com.maximuscooke.app.soccerboss.collections;

import java.util.Map;

import com.maximuscooke.app.soccerboss.CClub;
import com.maximuscooke.app.soccerboss.CCountry;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CClubMap extends CMap<Integer, CClub>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3922579955015505107L;
	
	public CClubMap()
	{
	}

	public CClubMap(int capacity)
	{
		super(capacity);
	}

	public CClubMap(Map<Integer, ? extends CClub> m)
	{
		super(m);
	}

	public void add(CClub c)
	{
		this.add(c.getIntegerID(), c);
	}

	public final CClubList getClubList(CCountry country)
	{
		CClubList results = new CClubList();

		for (CClub club : this.values())
		{
			if (club.getCountryID() == country.getIntegerID())
			{
				results.add(club);
			}
		}

		return results;
	}

	public final CClubMap getClubMap(CCountry country)
	{
		CClubMap results = new CClubMap();

		for (CClub club : this.values())
		{
			if (club.getCountryID() == country.getIntegerID())
			{
				results.add(club);
			}
		}

		return results;
	}

	public final CClubList getClubList(CArrayList<Integer> clubIds)
	{
		CClubList list = new CClubList(clubIds.getSize() + 1);

		for (int id : clubIds)
		{

			if (this.containsKey(id))
			{

				list.add(this.get(id));
			}
		}

		return list;
	}

	public final CClubMap getClubMap(CArrayList<Integer> clubIds)
	{
		CClubMap map = new CClubMap(clubIds.getSize() + 1);

		for (int id : clubIds)
		{
			if (this.containsKey(id))
			{

				map.add(this.get(id));
			}
		}

		return map;
	}
}
