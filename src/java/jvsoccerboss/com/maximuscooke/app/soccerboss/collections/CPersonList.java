package com.maximuscooke.app.soccerboss.collections;

import java.util.Collection;

import com.maximuscooke.app.soccerboss.CPerson;

public class CPersonList extends CList<CPerson>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2359474745757894576L;

	public CPersonList()
	{
	}

	public CPersonList(int capacity)
	{
		super(capacity);
	}

	public CPersonList(Collection<? extends CPerson> c)
	{
		super(c);
	}
}
