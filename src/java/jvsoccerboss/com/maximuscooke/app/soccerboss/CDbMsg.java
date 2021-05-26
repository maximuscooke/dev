package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.INotifyMessage;

public class CDbMsg implements INotifyMessage
{
	private CRegion mRegion=null;
	private CCountry mCountry=null;
	private CClub mClub=null;
	private CCompetition mCompetition=null;
	
	private String mMessage=null;
	
	private int mType=-1;
	private int mAction = -1;
	private int mProgressMin=0;
	private int mProgressMax=0;
	
	public CDbMsg(int type, int action)
	{
		this.mAction = action;
		
		this.mType = type;
	}
	
	public CDbMsg(int type, int action,CRegion r)
	{
		this(type, action);
		
		this.mRegion = r;
	}
	
	public CDbMsg(int type, int action, CCountry c)
	{
		this(type, action);
		
		this.mCountry = c;
	}
	
	public CDbMsg(int type, int action, CClub c)
	{
		this(type, action);
		
		this.mClub = c;
	}
	
	public CDbMsg(int type, int action, CCompetition c)
	{
		this(type, action);
		
		this.mCompetition = c;
	}
	
	public CDbMsg(int action, String msg)
	{
		this.mAction = action;
		
		this.setMessage(msg);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if (this.getAction() == CDatabase.ACTION_ADD)
		{
			sb.append("Adding ");
		}
		else if (this.getAction() == CDatabase.ACTION_DELETE)
		{
			sb.append("Deleting ");
		}
		
		if (this.getType() == CDatabase.REGION_TYPE)
		{
			sb.append("Region ");
			
			sb.append(this.getRegion().getName());
		}
		else if (this.getType() == CDatabase.COUNTRY_TYPE)
		{
			sb.append("Country ");
			
			sb.append(this.getCountry().getName());
		}
			
		return sb.toString();
	}

	public final CRegion getRegion()
	{
		return mRegion;
	}

	public final CCountry getCountry()
	{
		return mCountry;
	}

	public final int getAction()
	{
		return mAction;
	}

	public final int getType()
	{
		return mType;
	}

	public final String getMessage()
	{
		return mMessage;
	}

	public final void setMessage(String mMessage)
	{
		this.mMessage = mMessage;
	}

	public final CClub getClub()
	{
		return this.mClub;
	}

	public final CCompetition getCompetition()
	{
		return this.mCompetition;
	}

	public final void setCompetition(CCompetition comp)
	{
		this.mCompetition = comp;
	}
}
