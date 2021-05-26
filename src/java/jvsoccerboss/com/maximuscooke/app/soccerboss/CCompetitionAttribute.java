package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.IDescription;

public class CCompetitionAttribute extends CUniqueObject implements IDescription
{

	/**
	 * 
	 */

	public static final int ATTR_NOT_APPLICABLE = 0;
	public static final int ATTR_AUTOMATIC_PROMOTION = 10;
	public static final int ATTR_AUTOMATIC_RELEGATION = 20;
	public static final int ATTR_PLAYOFF_PLACE = 30;
	public static final int ATTR_QUALIFICATION = 40;

	private static final long serialVersionUID = 9173917689450974616L;

	private String mDesc = null;
	private CCompetitionPermission mPermission = null;
	private int mPositionID = -1;

	public CCompetitionAttribute(int id, String desc)
	{
		this(id, desc, null);
	}

	public CCompetitionAttribute(int id, String desc, CCompetitionPermission permission)
	{

	//	this.setDescription(desc);

		this.setIntegerID(id);

		this.setPermission(permission);
	}

	@Override
	public final String getDescription()
	{
		return mDesc;
	}

//	@Override
//	public final void setDescription(String desc)
//	{
//		mDesc = desc;
//	}

	public final CCompetitionPermission getPermission()
	{
		return this.mPermission;
	}

	public final void setPermission(CCompetitionPermission grant)
	{
		this.mPermission = grant;
	}

	@Override
	public String toString()
	{
		return this.getDescription();
	}

	public final int getPositionID()
	{
		return mPositionID;
	}

	public final void setPositionID(int positionID)
	{
		this.mPositionID = positionID;
	}
}
