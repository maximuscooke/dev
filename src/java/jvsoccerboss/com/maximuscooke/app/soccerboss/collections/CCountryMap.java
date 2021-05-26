package com.maximuscooke.app.soccerboss.collections;

import com.maximuscooke.app.soccerboss.CCountry;

public class CCountryMap extends CMap<Integer, CCountry>
{
	private static final long serialVersionUID = 5771262402372874127L;
	
	private int mCountryID=-1;
	private int mRegionID=-1;

	public CCountryMap()
	{
		this(16);
	}

	public CCountryMap(int capacity)
	{
		super(capacity);
	}
}
