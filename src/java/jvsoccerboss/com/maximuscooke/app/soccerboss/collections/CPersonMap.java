package com.maximuscooke.app.soccerboss.collections;

import java.util.Map;

import com.maximuscooke.app.soccerboss.CPerson;

public class CPersonMap extends CMap<Integer, CPerson>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2396362157745762872L;

	public CPersonMap()
	{
	}

	public CPersonMap(int capacity)
	{
		super(capacity);
	}

	public CPersonMap(int capacity, float loadFactor)
	{
		super(capacity, loadFactor);
	}

	public CPersonMap(Map<Integer, ? extends CPerson> m)
	{
		super(m);
	}
}
