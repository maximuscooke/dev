package com.maximuscooke.app.soccerboss;

import java.util.List;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.ICalendarEntry;
import com.maximuscooke.lib.common.IDateTime;
import com.maximuscooke.lib.common.IExportFile;

public class CFixture extends CUniqueObject implements ICalendarEntry, ICountryID, ICompetitionID, IRegionID, IExportFile
{
	private static final long serialVersionUID = 7667389008855785446L;
	
	public static final int HOME_VENUE = 10;
	public static final int AWAY_VENUE = 20;
	public static final int NEUTRAL_VENUE = 30;
	
	public static final int FXT_STATE_VALID = 0;
	public static final int FXT_STATE_DAY_CLASH = 10;
	public static final int FXT_STATE_GAP_TO_SMALL = 20;
	public static final int FXT_STATE_REVERSE_GAP_TO_SMALL = 30;
	public static final int FXT_STATE_CONSECUTIVE_VENUE = 40;
	
	private CGregorianCalendar mDateTime=null;
	
	private String mDescription=null;
	private String mHomeClubName=null;
	private String mAwayClubName=null;
	
	private int mRegionID=-1;
	private int mCountryID=-1;
	private int mCompetitionID=-1;
	private int mHomeClubID = -1;
	private int mAwayClubID = -1;
	private int mStatus=CFixture.FXT_STATE_VALID;
	
	public CFixture()
	{
	}

	public CFixture(int homeID, int awayID)
	{
		this(homeID, awayID, null);
	}

	public CFixture(int homeID, int awayID, CGregorianCalendar dt)
	{
		this( String.format("Club %02d", homeID), String.format("Club %02d", awayID), homeID, awayID, dt);
	}
	
	public CFixture(String homeClubName, String awayClubName, int homeID, int awayID, CGregorianCalendar dt)
	{
		super(String.format("%s v %s", homeClubName, awayClubName));
		
		this.setHomeClubID(homeID);
		
		this.setAwayClubID(awayID);
		
		this.setHomeClubName(homeClubName);
		
		this.setAwayClubName(awayClubName);
		
		this.setDateTime(dt);
	}

	@Override
	public String exportObject(String separator)
	{
		return null;
	}

	@Override
	public void importObject(List<String> values)
	{		
	}
	
	@Override
	public void doExport(CTextFile expFile)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("1" + CApplication.PIPE_SEPARATOR); // version
		
		sb.append(CApplication.toNullPtrString( this.getName() ) + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getIntegerID() + CApplication.PIPE_SEPARATOR);
		
		sb.append(CApplication.toNullPtrString(this.getDescription()) + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getRegionID() + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getCountryID() + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getCompetitionID() + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getHomeClubID() + CApplication.PIPE_SEPARATOR);
		
		sb.append(this.getAwayClubID() + CApplication.PIPE_SEPARATOR);
				
		sb.append(this.getStatus() + CApplication.PIPE_SEPARATOR);
		
		sb.append( (mDateTime == null ? CApplication.NULL_PTR_STRING : CGregorianCalendar.toExportString(this.mDateTime, CApplication.PIPE_SEPARATOR)) + CApplication.PIPE_SEPARATOR);
		
		sb.append(CApplication.toNullPtrString(this.getHomeClubName()) + CApplication.PIPE_SEPARATOR);
		
		sb.append(CApplication.toNullPtrString(this.getAwayClubName()) + CApplication.PIPE_SEPARATOR);
				
		expFile.addLine( sb.toString() );
	}

	@Override
	public void doImport(CTextFile f, long linenum)
	{
		int iLinenum = (int)(linenum);
		
		List<String> list = CApplication.splitExportString(f.getLineAt( iLinenum ), CApplication.PIPE_SEPARATOR);
						
		int index = 0;
		
		int version = Integer.parseInt(list.get( index++ ));
		
		this.setName( CApplication.fromNullPtrString(list.get(index++)));
		
		this.setIntegerID(Integer.parseInt(list.get( index++)) );
		
		this.setDescription( CApplication.fromNullPtrString(list.get(index++)));
		
		this.setRegionID( Integer.parseInt(list.get( index++)));
		
		this.setCountryID( Integer.parseInt(list.get( index++)));
		
		this.setCompetitionID( Integer.parseInt(list.get( index++)));
		
		this.setHomeClubID( Integer.parseInt(list.get( index++)));
		
		this.setAwayClubID( Integer.parseInt(list.get( index++)));
				
		this.setStatus( Integer.parseInt(list.get( index++)));
												
		if (CApplication.isNullPtrString(list.get(index)))
		{
			++index;
			
			this.setDateTime(null);
		}
		else
		{
			this.setDateTime( CGregorianCalendar.parseExportString(list, index) );

			index += 6; // 6 is number of fields in date (e.g year, month, datOfMonth, hour , minutes, seconds)
		}
				
		this.setHomeClubName(CApplication.fromNullPtrString(list.get(index++)));
				
		this.setAwayClubName((CApplication.fromNullPtrString(list.get(index++))));
	}
	
	public String toShortString(int clubID)
	{
		StringBuilder sb = new StringBuilder();

		sb.append((this.isHomeOrAway(clubID) == CFixture.HOME_VENUE) ? String.format("(H) %s", this.getAwayClubName()) : String.format("(A) %s", this.getHomeClubName()));
		
		sb.append(" - ");
		
		sb.append(CGregorianCalendar.toString(this.getDateTime(), "EEE dd MMM yyyy hh:mm"));			

		return sb.toString();
	}
	
	public final String toKey()
	{
		return String.format("%02d:%02d:%02d:", this.getCompetitionID(), this.getHomeClubID(), this.getAwayClubID());
	}
	
	public final int isHomeOrAway(int clubID)
	{
		return (this.mHomeClubID == clubID) ? CFixture.HOME_VENUE : (this.mAwayClubID == clubID) ? CFixture.AWAY_VENUE : -1;
	}
	
	public final boolean isPlayingInFixture(int clubID)
	{
		return (this.mHomeClubID == clubID || this.mAwayClubID == clubID) ? true : false;
	}
	
	public final boolean isPlayingOn(IDateTime dt)
	{
		return (this.mDateTime == null) ? false : (dt.getYear() == this.mDateTime.getYear() && dt.getMonth() == this.mDateTime.getMonth() && dt.getDayOfMonth() == this.mDateTime.getDayOfMonth());
	}
	
	public final void unSchedule()
	{
		this.setDateTime(null);
		
		this.setStatus(CFixture.FXT_STATE_VALID);
	}

	public final void setCountryID(int id)
	{
		this.mCountryID = id;
	}

	@Override
	public final int getCountryID()
	{
		return this.mCountryID;
	}
	
	public final void setCompetitionID(int id)
	{
		this.mCompetitionID = id;
	}

	@Override
	public final int getCompetitionID()
	{
		return this.mCompetitionID;
	}

	public final int getHomeClubID()
	{
		return this.mHomeClubID;
	}

	public final void setHomeClubID(int id)
	{
		this.mHomeClubID = id;
	}

	public final int getAwayClubID()
	{
		return this.mAwayClubID;
	}

	public final void setAwayClubID(int id)
	{
		this.mAwayClubID = id;
	}

	public final int getStatus()
	{
		return this.mStatus;
	}

	public final void setStatus(int status)
	{
		this.mStatus = status;
	}
	
	public final void setRegionID(int id)
	{
		this.mRegionID = id;
	}
	
	@Override
	public final int getRegionID()
	{
		return this.mRegionID;
	}
	
	public final CGregorianCalendar getDateTime()
	{
		return this.mDateTime;
	}

	public final void setDateTime(CGregorianCalendar dt)
	{
		this.mDateTime = dt;
	}

	public final void setDescription(String desc)
	{
		this.mDescription = desc;
	}
	
	@Override
	public final String getDescription()
	{
		return this.mDescription;
	}
	
	public final boolean isScheduled()
	{
		return this.getDateTime() != null;
	}

	public final String getHomeClubName()
	{
		return this.mHomeClubName;
	}

	public final void setHomeClubName(String nm)
	{
		this.mHomeClubName = nm;
	}

	public final String getAwayClubName()
	{
		return this.mAwayClubName;
	}

	public final void setAwayClubName(String nm)
	{
		this.mAwayClubName = nm;
	}
}
