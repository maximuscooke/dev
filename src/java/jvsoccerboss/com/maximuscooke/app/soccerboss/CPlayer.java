package com.maximuscooke.app.soccerboss;

public class CPlayer extends CPerson
{
	private static final long serialVersionUID = -5063908918057645297L;
	
	private float mHeight=0.0F;

	public CPlayer()
	{
	}

	public CPlayer(String name, int id)
	{
		super(name, id);
	}

	@Override
	public final int getPersonType()
	{
		return CPerson.PERSON_PLAYER_MASK;
	}

	public final float getHeight()
	{
		return mHeight;
	}

	public final void setHeight(float height)
	{
		this.mHeight = height;
	}
}
