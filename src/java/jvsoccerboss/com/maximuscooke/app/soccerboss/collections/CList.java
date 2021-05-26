package com.maximuscooke.app.soccerboss.collections;

import java.util.Collection;

import com.maximuscooke.lib.common.collections.CArrayList;

public class CList<T> extends CArrayList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6889488968365212239L;

	public CList()
	{
	}

	public CList(int capacity)
	{
		super(capacity);
	}

	public CList(Collection<? extends T> c)
	{
		super(c);
	}

}
