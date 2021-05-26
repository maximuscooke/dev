package com.maximuscooke.app.soccerboss.collections;

import java.util.Collection;

import com.maximuscooke.app.soccerboss.CClub;

public class CClubList extends CList<CClub>
{
	private static final long serialVersionUID = -541562644796910794L;

	public CClubList()
	{
	}

	public CClubList(int capacity)
	{
		super(capacity);
	}

	public CClubList(Collection<? extends CClub> c)
	{
		super(c);
	}
}
