package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.*;

public abstract class CUniqueObject implements IIntegerID, IName, ISerializable
{
	private static final long serialVersionUID = -859554503742462147L;

	private String mName = null;
	
	private int mID = -1;

	public CUniqueObject()
	{
	}

	public CUniqueObject(String name)
	{
		this.setName(name);
	}
	
	public CUniqueObject(String name, int id)
	{
		this.setName(name);
		
		this.setIntegerID(id);
	}

	@Override
	public final int getIntegerID()
	{
		return this.mID;
	}

	public final void setIntegerID(int id)
	{
		this.mID = id;
	}

	@Override
	public final String getName()
	{
		return this.mName;
	}

	@Override
	public final void setName(String nm)
	{
		this.mName = nm;
	}

	@Override
	public String toString()
	{
		return this.getName();
	}
}
