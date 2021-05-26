package com.maximuscooke.lib.common.collections;

import java.util.Collection;
import java.util.Enumeration;

import com.maximuscooke.lib.common.IDateTime;

public class CDateTimeList extends CArrayList<IDateTime>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 693541661530636552L;

	public CDateTimeList()
	{
	}

	public CDateTimeList(int capacity)
	{
		super(capacity);
	}

	public CDateTimeList(Collection<? extends IDateTime> c)
	{
		super(c);
	}

	public CDateTimeList(Enumeration<? extends IDateTime> c)
	{
		super(c);
	}
}
