package com.maximuscooke.app.soccerboss;

public class CNonPlayer extends CPerson
{
	private static final long serialVersionUID = 8037850975972744414L;
	
	private int mType = 0;

	public CNonPlayer()
	{
	}

	public CNonPlayer(String name, int type, int id)
	{
		super(name, id);
		
		this.mType = 0;
	}

	public final void setPersonType(int type)
	{
		this.mType = type;
	}
	
	@Override
	public final int getPersonType()
	{
		return this.mType;
	}

}
