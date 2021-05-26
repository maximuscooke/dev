package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CGregorianCalendar;

public abstract class CPerson extends CGameObject
{
	private static final long serialVersionUID = 194038598426984792L;

	public static final int PERSON_PLAYER_MASK = (1<<0);
	public static final int PERSON_MATCH_OFFICIAL_MASK = (1<<1);
	public static final int PERSON_MANAGER_MASK = (1<<2);
	public static final int PERSON_COACH_MASK = (1<<3);
	public static final int PERSON_SCOUT_MASK = (1<<4);
	public static final int PERSON_PHYSIO_MASK = (1<<5);
	public static final int PERSON_CHAIRMAN_MASK = (1<<6);
	public static final int PERSON_MATCH_OFFICAL_MASK = (1<<7);
	
	private String mForename=null;
	private String mSurname=null;
	private CGregorianCalendar mDOB = null;
	private int mCountryID=-1;

	public CPerson()
	{
	}

	public CPerson(String name, int id)
	{
		super(name, id);
	}

	public abstract int getPersonType();

	public final String getForename()
	{
		return this.mForename;
	}

	public final void setForename(String nm)
	{
		this.mForename = nm;
	}

	public final String getSurname()
	{
		return this.mSurname;
	}

	public final void setSurname(String nm)
	{
		this.mSurname = nm;
	}

	public final CGregorianCalendar getDOB()
	{
		return this.mDOB;
	}

	public final void setDOB(CGregorianCalendar dob)
	{
		this.mDOB = dob;
	}

	public final int getCountryID()
	{
		return this.mCountryID;
	}

	public final void setCountryID(int id)
	{
		this.mCountryID = id;
	}
}
