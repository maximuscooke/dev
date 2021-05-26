package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.collections.CArrayList;

public class CClub extends CGameObject implements ICountryID, IRegionID, IStatus, INationalLeagueID
{
	private static final long serialVersionUID = -6055309755881676835L;
	
	
	private CArrayList<Integer> mFirstTeam = new CArrayList<Integer>();
	private CArrayList<Integer> mReserveTeam = new CArrayList<Integer>();
	private CArrayList<Integer> mYouthTeam = new CArrayList<Integer>();
	
	private String mNickName = null;
	private String mStadiumName=null;
	
	private int mOwnerID=-1;
	private int mDirectorID=-1;
	private int mFirstTeamManagerID=-1;
	private int mReserveTeamManagerID=-1;
	private int mYouthTeamManagerID=-1;
	private int mCoachID=-1;
	private int mDoctorID=-1;
	private int mPhysioID=-1;	
	private int mStadiumCapacity=0;
	private int mCountryID = -1;
	private int mRegionID = -1;
	private int mNationalLeagueID=-1;
	private int mStatus=0;

	public CClub()
	{
	}

	public CClub(String name, int id, int regionID, int countryID)
	{
		super(name, id);
		
		this.setRegionID(regionID);

		this.setCountryID(countryID);
		
		this.setImageName16("wi0165-16.png");
	}

	@Override
	public final int getCountryID()
	{
		return this.mCountryID;
	}
	
	@Override
	public void setCountryID(int id)
	{
		this.mCountryID = id;
	}
	
	@Override
	public void setRegionID(int id)
	{
		this.mRegionID = id;
	}

	@Override
	public int getRegionID()
	{
		return this.mRegionID;
	}
	
	public final String getNickName()
	{
		return this.mNickName;
	}

	public final void setNickName(String nm)
	{
		this.mNickName = nm;
	}

	public final String getStadiumName()
	{
		return this.mStadiumName;
	}

	public final void setStadiumName(String nm)
	{
		this.mStadiumName = nm;
	}

	public final int getStadiumCapacity()
	{
		return this.mStadiumCapacity;
	}

	public final void setStadiumCapacity(int capacity)
	{
		this.mStadiumCapacity = capacity;
	}

	@Override
	public int getStatus()
	{
		return this.mStatus;
	}

	@Override
	public void setStatus(int status)
	{
		this.mStatus = status;
	}

	@Override
	public final int getNationalLeagueID()
	{
		return this.mNationalLeagueID;
	}

	@Override
	public final void setNationalLeagueID(int id)
	{
		this.mNationalLeagueID = id;
	}

	public final CArrayList<Integer> getFirstTeam()
	{
		return this.mFirstTeam;
	}

	public final void setFirstTeam(CArrayList<Integer> ids)
	{
		this.mFirstTeam = ids;
	}

	public final CArrayList<Integer> getReserveTeam()
	{
		return this.mReserveTeam;
	}

	public final void setReserveTeam(CArrayList<Integer> ids)
	{
		this.mReserveTeam = ids;
	}

	public final CArrayList<Integer> getYouthTeam()
	{
		return this.mYouthTeam;
	}

	public final void setYouthTeam(CArrayList<Integer> ids)
	{
		this.mYouthTeam = ids;
	}

	public final int getOwnerID()
	{
		return this.mOwnerID;
	}

	public final void setOwnerID(int id)
	{
		this.mOwnerID = id;
	}

	public final int getDirectorID()
	{
		return this.mDirectorID;
	}

	public final void setDirectorID(int id)
	{
		this.mDirectorID = id;
	}

	public final int getReserveTeamManagerID()
	{
		return this.mReserveTeamManagerID;
	}

	public final void setReserveTeamManagerID(int id)
	{
		this.mReserveTeamManagerID = id;
	}

	public final int getYouthTeamManagerID()
	{
		return this.mYouthTeamManagerID;
	}

	public final void setYouthTeamManagerID(int id)
	{
		this.mYouthTeamManagerID = id;
	}

	public final int getCoachID()
	{
		return this.mCoachID;
	}

	public final void setCoachID(int id)
	{
		this.mCoachID = id;
	}

	public final int getDoctorID()
	{
		return this.mDoctorID;
	}

	public final void setDoctorID(int id)
	{
		this.mDoctorID = id;
	}

	public final int getPhysioID()
	{
		return this.mPhysioID;
	}

	public final void setPhysioID(int id)
	{
		this.mPhysioID = id;
	}
}
