package com.maximuscooke.app.soccerboss;

import java.util.List;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CRange;
import com.maximuscooke.lib.common.CSerialize;
import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.IDateTime;
import com.maximuscooke.lib.common.IExportFile;
import com.maximuscooke.lib.common.INotify;
import com.maximuscooke.lib.common.INotifyListener;
import com.maximuscooke.lib.common.INotifyMessage;
import com.maximuscooke.lib.common.ISerializable;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CHashMap;
import com.maximuscooke.lib.common.exceptions.CKeyNotFoundException;

public class CSchedule implements ISerializable, IExportFile, INotify
{
	private static final long serialVersionUID = 8638100254918681656L;
	
	public static final String SCHEDULE_KEY="<SCHEDULE>";
	public static final String SETTINGS_KEY="<SETTINGS>";
	public static final String FIXTURES_KEY="<FIXTURES>";
	
	public static final byte BIT_MODIFIED = 0;
	public static final byte BIT_ENABLE_NOTIFY = 1;
	
	public static final int ACTION_SCHEDULE_FXT = 10;
	public static final int ACTION_UNSCHEDULE_FXT = 20;
	public static final int ACTION_UNSCHEDULE_CLUBS = 30;
	public static final int ACTION_UNSCHEDULE_ROW = 40;
	public static final int ACTION_CLEAR_SCHEDULE = 50;
	public static final int ACTION_EXTEND_SCHEDULE = 60;
	public static final int ACTION_MOVE_ROW = 70;
	public static final int ACTION_PROPERTIES_CHANGED = 80;
	public static final int ACTION_VALIDATE_SCHEDULE = 90;
	public static final int ACTION_IMPORT_SCHEDULE = 100;
	public static final int ACTION_TRIM_SCHEDULE = 110;
	public static final int ACTION_SCHEDULE_REVERSE_FXT = 120;
	public static final int ACTION_AUTO_NAME = 130;
	public static final int ACTION_UNSCHEDULE_ALL = 40;

	private CGregorianCalendar mStartDate=null;
	private CGregorianCalendar mEndDate=null;
	
	private CArrayList<CFixture> mFixtures = null;
	
	private static int mResult1;
		
	private int mMaxClubs = 0;
	private int mFixtureGap = 0;
	private int mMaxConsecutive=0;
	private int mReverseFixtureGap=0;
	private int mStatus=0;
	
	private transient CArrayList<INotifyListener> mListeners = null;

	public CSchedule()
	{
		this.setEnableNotifications(true);
	}
	
	public final void init()
	{
		this.createFixtures(this.getMaxClubs());
		
		this.setIsModified(true);
	}
		
	public final void init(int maxClubs, int fxtGap, int maxConsecutive, int reverseGap, CGregorianCalendar start, CGregorianCalendar end)
	{
		this.init( maxClubs,  fxtGap, maxConsecutive, reverseGap, start, end, null);
	}
	
	public final void init(int maxClubs, int fxtGap, int maxConsecutive, int reverseGap, CGregorianCalendar start, CGregorianCalendar end, List<String> clubNames)
	{
		this.setMaxClubs(maxClubs);
		
		this.setFixtureGap(fxtGap);
		
		this.setMaxConsecutive(maxConsecutive);
		
		this.setReverseFixtureGap(reverseGap);
		
		this.setStartDate(start);
		
		this.setEndDate(end);
		
		this.init();
	}
		
	private void createFixtures(int maxClubs)
	{
		this.mFixtures = new CArrayList<CFixture>(maxClubs * maxClubs);
		
		int id = 1;
		
		for(int i = 0; i < maxClubs; i++)
		{
			for(int j = 0; j < maxClubs; j++)
			{
				if (i != j)
				{
					CFixture f = new CFixture( (i+1), (j+1));
					
					f.setIntegerID(id++);
					
					this.mFixtures.add( f );
				}
			}
		}
	}
	
	public static CSchedule load(String dir, String fName) throws Exception
	{
		return load(dir, fName, CSerialize.SERIALIZE_FORMAT_XML);
	}
	
	public static CSchedule load(String dir, String fName, int format) throws Exception
	{
		CSchedule sch = (CSchedule) CSerialize.deSerializeObjects(dir, fName, format);
		
		sch.setIsModified(false);
		
		return sch;
	}
	
	public void save(String dir, String fName) throws Exception
	{
		CSerialize.serializeObjects(dir, fName, CSerialize.SERIALIZE_FORMAT_XML, this);
	}
	
	public void save(String dir, String fName, int format) throws Exception
	{
		CSerialize.serializeObjects(dir, fName, format, this);
		
		this.setIsModified(false);
	}
	
	public final void autoName(CArrayList<String> clubNames)
	{
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				int hID = f.getHomeClubID() - 1;
				
				int aID = f.getAwayClubID() - 1;
				
				f.setName( String.format("%s vs %s", clubNames.getAt(hID), clubNames.getAt(aID) ));
				
				f.setHomeClubName(clubNames.getAt(hID));
				
				f.setAwayClubName(clubNames.getAt(aID));
				
				f.setDescription( null );				
			}
			
			this.notify(CSchedule.ACTION_AUTO_NAME, null);
		}
		
		this.setIsModified(true);
	}
	
	public final CFixture findFixture(int homeID, int awayID)
	{
		if (this.mFixtures != null)
		{
			for(CFixture fxt : this.mFixtures)
			{
				if (fxt.getHomeClubID() == homeID && fxt.getAwayClubID() == awayID)
				{
					return fxt;
				}
			}
		}
		
		return null;
	}
	
	public final CFixture findFixture(int id)
	{
		if (this.mFixtures != null)
		{
			for(CFixture fxt : this.mFixtures)
			{
				if (fxt.getIntegerID() == id)
				{
					return fxt;
				}
			}
		}
		
		return null;
	}
		
	@Override
	public void doExport(CTextFile expFile)
	{
		expFile.addLine(CSchedule.SCHEDULE_KEY);
		
		expFile.addLine(CSchedule.SETTINGS_KEY);
		
		expFile.addLine( String.format("%d%s%d%s%d%s%d%s%d%s%s%s%s",  	1, // version 
																							CApplication.PIPE_SEPARATOR,
																							this.getMaxClubs(),
																							 CApplication.PIPE_SEPARATOR,
																							 this.getFixtureGap(),
																							 CApplication.PIPE_SEPARATOR,
																							 this.getMaxConsecutive(),
																							 CApplication.PIPE_SEPARATOR,
																							 this.getReverseFixtureGap(),
																							 CApplication.PIPE_SEPARATOR,
																							 CGregorianCalendar.toExportString(this.getStartDate(), CApplication.PIPE_SEPARATOR),
																							 CApplication.PIPE_SEPARATOR,
																							 CGregorianCalendar.toExportString(this.getEndDate(), CApplication.PIPE_SEPARATOR),
																							 CApplication.PIPE_SEPARATOR ));
			
		expFile.addLine(CSchedule.SETTINGS_KEY);
		
		expFile.addLine(CSchedule.FIXTURES_KEY);
		
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				f.doExport(expFile);
			}			
		}
		
		expFile.addLine(CSchedule.FIXTURES_KEY);
		
		expFile.addLine(CSchedule.SCHEDULE_KEY);
	}

	@Override
	public void doImport(CTextFile f, long linenum) throws Exception
	{
		this.mFixtures = new CArrayList<CFixture>(512);
		
		CRange<Long> range = f.findMarkerRange(CSchedule.SETTINGS_KEY);
		
		if (range.getMin() >= 0 && range.getMax() >= 0)
		{
			List<String> settings = CApplication.splitExportString(f.getLineAt( (int)(long)range.getMin() + 1 ), CApplication.PIPE_SEPARATOR);
			
			int version = Integer.parseInt(settings.get(0));
			
			this.setMaxClubs( Integer.parseInt(settings.get(1)) );
			
			this.setFixtureGap( Integer.parseInt(settings.get(2)) );
			
			this.setMaxConsecutive( Integer.parseInt(settings.get(3)) );
			
			this.setReverseFixtureGap( Integer.parseInt(settings.get(4)) );
			
			this.setStartDate( new CGregorianCalendar( 	Integer.parseInt(settings.get(5)), 
																		Integer.parseInt(settings.get(6)),
																		Integer.parseInt(settings.get(7)),
																		Integer.parseInt(settings.get(8)),
																		Integer.parseInt(settings.get(9)),
																		Integer.parseInt(settings.get(10))));
			
			this.setEndDate( 	new CGregorianCalendar( 	Integer.parseInt(settings.get(11)), 
																		Integer.parseInt(settings.get(12)),
																		Integer.parseInt(settings.get(13)),
																		Integer.parseInt(settings.get(14)),
																		Integer.parseInt(settings.get(15)),
																		Integer.parseInt(settings.get(16))));
			
			range = f.findMarkerRange(CSchedule.FIXTURES_KEY);
						
			for(long ln = range.getMin()+1; ln < range.getMax(); ln++)
			{
				CFixture newFxt = new CFixture();
				
				newFxt.doImport(f, ln );
								
				this.mFixtures.add(newFxt);
			}
		}
	}
	
	public final void trim()
	{
		if (this.mFixtures != null)
		{
			CArrayList<CGregorianCalendar>  dates = this.getFixtureDates();
			
			dates.sort(CCollections.mDefaultSortDateTime);
			
			if (dates.getSize() > 0)
			{
				this.setStartDate( dates.getAt(0) );
								
				this.setEndDate( dates.getLast() );
				
				this.notify(CSchedule.ACTION_TRIM_SCHEDULE, null);
			}
		}
	}
	
	public final void scheduleReverse(CGregorianCalendar src, CGregorianCalendar dest) throws CScheduleValidationException
	{
		if (this.mFixtures != null)
		{
			CArrayList<CFixture> fxtlist = this.getFixtures(src);
			
			this.setEnableNotifications(false);
			
			this.unSchedule(dest);
												
			for(CFixture fxt : fxtlist)
			{
				CFixture revfxt = getReverseFixture(fxt); 
				
				if (revfxt != null && !revfxt.isScheduled()) 
				{ 
					this.schedule(revfxt, dest);
				}
			}
			
			this.setEnableNotifications(true);
			
			this.notify(CSchedule.ACTION_SCHEDULE_REVERSE_FXT, null);
		}
	}
		
	public final void schedule(CFixture fxt, CGregorianCalendar dt) throws CScheduleValidationException
	{		
		fxt.setStatus(CFixture.FXT_STATE_VALID);

		this.validate(fxt, dt, true);
		
		this.setIsModified(true);

		fxt.setDateTime(dt);
		
		this.validateConsecutiveMatches(fxt);
		
		this.notify(CSchedule.ACTION_SCHEDULE_FXT, null);
	}

	public final void move(CGregorianCalendar src, CGregorianCalendar dest)
	{
		if (this.mFixtures != null)
		{
			this.setEnableNotifications(false);
			
			this.unSchedule(dest);
			
			CArrayList<CFixture> fxtlist = this.getFixtures(src);
			
			fxtlist.forEach(fxt -> { if (fxt.isScheduled() ) { fxt.setDateTime(dest); } } );
			
			this.setEnableNotifications(true);
			
			this.notify(CSchedule.ACTION_MOVE_ROW, null);
		}
	}
	
	public final void validate()
	{
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				if (f.isScheduled())
				{
					try
					{
						this.validate(f, f.getDateTime(), false);
					} 
					catch (CScheduleValidationException e)
					{
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
	
	private final int validate(CFixture fxt, CGregorianCalendar dt, boolean bValidateState) throws CScheduleValidationException
	{
		this.validateDay(fxt, fxt.getHomeClubName(), fxt.getHomeClubID(), dt);
		
		this.validateDay(fxt, fxt.getAwayClubName(), fxt.getAwayClubID(), dt);
		
		this.validateGap(fxt, fxt.getHomeClubName(), fxt.getHomeClubID(), dt);
		
		this.validateGap(fxt, fxt.getAwayClubName(), fxt.getAwayClubID(), dt);
		
		this.validateReverseGap(fxt, dt);
						
		return CFixture.FXT_STATE_VALID;
	}
	
	private void validateConsecutiveMatches(CFixture fxt)
	{
		CArrayList<CFixture> hlist = this.getFixtureSchedule(fxt.getHomeClubID());
		
		hlist.forEach( f -> f.setStatus(CFixture.FXT_STATE_VALID) );
				
		CArrayList<CFixture> alist = this.getFixtureSchedule(fxt.getAwayClubID());

		alist.forEach( f -> f.setStatus(CFixture.FXT_STATE_VALID) );

		validateConsecutiveMatches(fxt.getHomeClubName(), fxt.getHomeClubID(), CFixture.HOME_VENUE, "HOME", hlist);
		
		validateConsecutiveMatches(fxt.getHomeClubName(), fxt.getHomeClubID(), CFixture.AWAY_VENUE, "AWAY", hlist);
		
		validateConsecutiveMatches(fxt.getAwayClubName(), fxt.getAwayClubID(), CFixture.HOME_VENUE, "HOME", alist);

		validateConsecutiveMatches(fxt.getAwayClubName(), fxt.getAwayClubID(), CFixture.AWAY_VENUE, "AWAY", alist);
	}
	
	private void validateConsecutiveMatches(String clubName, int clubID, int venue, String msg, CArrayList<CFixture> fxtlist)
	{
		if (fxtlist != null)
		{
			CCollections.sortByDateTimeStamp(fxtlist);
			
			int runCount = 0;
			
			for (int i = 0; i < fxtlist.getSize(); i++)
			{
				CFixture fxt = fxtlist.getAt(i);
												
				if (fxt.isHomeOrAway(clubID) == venue)
				{
					if (++runCount > this.getMaxConsecutive())
					{
						String str = String.format(" %s has to many consecutive %s matches, maximum is %02d", clubName, msg, this.getMaxConsecutive());
						
						int t = i;
						
						for (int j = 0; j < this.getMaxConsecutive() + 1; j++, --t)
						{
							if(fxtlist.getAt(t).getStatus() == CFixture.FXT_STATE_VALID )
							{								
								fxtlist.getAt(t).setStatus(CFixture.FXT_STATE_CONSECUTIVE_VENUE);
								
								fxtlist.getAt(t).setDescription(str);
								
							}
						}
					}
				} 
				else
				{
					runCount = 0;
				}
			}
		}
	}
				
	private int validateReverseGap(CFixture fxt, CGregorianCalendar dt) throws CScheduleValidationException
	{
		CFixture revfxt = this.getReverseFixture(fxt.getHomeClubID(), fxt.getAwayClubID());
		
		if (revfxt != null && revfxt.isScheduled())
		{
			int dayDiff = Math.abs((int)CGregorianCalendar.getDifferenceInDays(dt, revfxt.getDateTime()));
						
			if (dayDiff < this.getReverseFixtureGap()+1)
			{
				fxt.setStatus(CFixture.FXT_STATE_REVERSE_GAP_TO_SMALL);
				
				String str = String.format("Gap between reverse fixtures not large enough, must be at least %2d clear days", this.getReverseFixtureGap());
				
				fxt.setDescription(str);
				
				throw new CScheduleValidationException(fxt, CFixture.FXT_STATE_REVERSE_GAP_TO_SMALL, fxt.getHomeClubID(), dt, str );
			}
		}
		
		return CFixture.FXT_STATE_VALID;
	}
	
	private int validateDay(CFixture fxt, String clubName, int clubID, CGregorianCalendar dt) throws CScheduleValidationException
	{
		if (this.isClubPlayingOnDate(clubID, dt))
		{
			fxt.setStatus(CFixture.FXT_STATE_DAY_CLASH);
						
			String str = String.format("%s is already playing on date %s", clubName, dt);
			
			fxt.setDescription(str);

			throw new CScheduleValidationException(fxt, CFixture.FXT_STATE_DAY_CLASH, clubID, dt, str );
		}
		
		return CFixture.FXT_STATE_VALID;
	}
	
	public final int validateGap(CFixture fxt, String clubName, int clubID, CGregorianCalendar dt) throws CScheduleValidationException
	{
		CArrayList<CGregorianCalendar> fxtlist = this.getFixtureDates(clubID);
		
		fxtlist.add(dt);
		
		if (fxtlist.getSize() > 1)
		{
			fxtlist.sort(CCollections.mDefaultSortDateTime);
			
			for(int i = 0; i < fxtlist.getSize() - 1; i++)
			{
				int dayDiff = Math.abs((int)CGregorianCalendar.getDifferenceInDays(fxtlist.getAt(i), fxtlist.getAt(i+1)));
				
				if (dayDiff < this.getFixtureGap()+1)
				{
					fxt.setStatus(CFixture.FXT_STATE_GAP_TO_SMALL);
					
					String str = String.format("%s gap between fixtures not large enough, must be at least %2d clear days", clubName, this.getFixtureGap());
					
					fxt.setDescription(str);
					
					throw new CScheduleValidationException(fxt, CFixture.FXT_STATE_GAP_TO_SMALL, clubID, dt, str );
				}
			}
		}
		
		return CFixture.FXT_STATE_VALID;
	}
	
	public final CFixture getReverseFixture(CFixture fxt)
	{
		return getReverseFixture(fxt.getHomeClubID(), fxt.getAwayClubID());
	}

	public final CFixture getReverseFixture(int hID, int aID)
	{
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				if (f.getHomeClubID() == aID && f.getAwayClubID() == hID)
				{
					return f;
				}
			}			
		}
		
		return null;
	}
	
	public final void unScheduleAll()
	{
		if (this.mFixtures != null)
		{
			this.setEnableNotifications(false);
			
			this.mFixtures.forEach( f -> unSchedule(f, false) );
			
			this.setEnableNotifications(true);
			
			this.notify(CSchedule.ACTION_UNSCHEDULE_ALL, null);
		}
	}
	
	public final void unSchedule(List<IDateTime> dates)
	{
		this.setEnableNotifications(false);
		
		for(IDateTime dt : dates)
		{
			unSchedule(dt);
		}
		
		this.setEnableNotifications(true);
		
		this.notify(CSchedule.ACTION_UNSCHEDULE_ROW, null);
	}
	
	public final void unSchedule(CFixture fxt)
	{
		unSchedule(fxt, true);
	}
	
	private final void unSchedule(CFixture fxt, boolean bRunValidation)
	{
		fxt.unSchedule();
		
		this.setIsModified(true);
		
		if (bRunValidation)
		{
			this.validateConsecutiveMatches(fxt);			
		}
		
		this.notify(CSchedule.ACTION_UNSCHEDULE_FXT, null);
	}
		
	public final void unSchedule(IDateTime dt)
	{
		if (this.mFixtures != null)
		{			
			this.mFixtures.forEach( f -> { if (f.isPlayingOn(dt) ) { unSchedule(f, true); } } );
		}
	}
	
	public final void unScheduleClubs(List<Integer> clubIDs)
	{
		if (this.mFixtures != null && clubIDs != null)
		{
			this.setEnableNotifications(false);
						
			for(Integer clubID : clubIDs)
			{
				this.mFixtures.forEach( f -> { if (f.isPlayingInFixture(clubID)) { unSchedule(f, true); } });				
			}
						
			this.setEnableNotifications(true);
			
			this.setIsModified(true);
			
			this.notify(CSchedule.ACTION_UNSCHEDULE_CLUBS, null);
		}
	}
	
	public final void unScheduleErrors()
	{
		if (this.mFixtures != null)
		{
			this.setEnableNotifications(false);
			
			this.mFixtures.forEach( f -> { if (f.getStatus() != CFixture.FXT_STATE_VALID) { unSchedule(f, false); } });
			
			this.setEnableNotifications(true);
			
			this.setIsModified(true);
			
			this.notify(CSchedule.ACTION_UNSCHEDULE_FXT, null);
		}
	}
	
	public final int getMaxFixturesPerDay()
	{
		return this.getMaxClubs() / 2;
	}

	public final CGregorianCalendar getStartDate()
	{
		return this.mStartDate;
	}

	public final void setStartDate(CGregorianCalendar dt)
	{
		this.mStartDate = dt;
	}

	public final CGregorianCalendar getEndDate()
	{
		return this.mEndDate;
	}

	public final void setEndDate(CGregorianCalendar dt)
	{
		this.mEndDate = dt;
	}

	public final int getMaxClubs()
	{
		return this.mMaxClubs;
	}

	public final void setMaxClubs(int max)
	{
		this.mMaxClubs = max;
	}

	public final int getFixtureGap()
	{
		return this.mFixtureGap;
	}

	public final void setFixtureGap(int gap)
	{
		this.mFixtureGap = gap;
	}

	public final int getMaxConsecutive()
	{
		return this.mMaxConsecutive;
	}

	public final void setMaxConsecutive(int max)
	{
		this.mMaxConsecutive = max;
	}

	public final int getReverseFixtureGap()
	{
		return this.mReverseFixtureGap;
	}

	public final void setReverseFixtureGap(int gap)
	{
		this.mReverseFixtureGap = gap;
	}
	
	public final int getIDFromName(String clubName) throws CKeyNotFoundException
	{
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				if (f != null)
				{
					if (f.getHomeClubName() != null && f.getHomeClubName().equalsIgnoreCase(clubName))
					{
						return f.getHomeClubID();
					}
					else if (f.getAwayClubName() != null && f.getAwayClubName().equalsIgnoreCase(clubName))
					{
						return f.getAwayClubID();
					}					
				}
			}			
		}
		
		throw new CKeyNotFoundException(String.format("%s not found", clubName));
	}
	
	public final boolean isClubPlayingOnDate(int clubID, IDateTime dt)
	{
		CArrayList<CFixture> fxtlist = this.getFixtures(dt);
		
		for(CFixture f : fxtlist)
		{
			if(f.getHomeClubID() == clubID || f.getAwayClubID() == clubID)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		mResult1 = 0;
		
		this.mFixtures.forEach( fxt -> { if (fxt.isScheduled()) { ++mResult1; } } );
		
		return String.format("Total=%03d, S=%03d, U=%03d", this.mFixtures.getSize(), mResult1,  this.mFixtures.getSize() - mResult1);
	}

	@Override
	public void register(INotifyListener listener)
	{
		if (this.mListeners == null)
		{
			this.mListeners = new CArrayList<INotifyListener>();
		}

		this.mListeners.add(listener);

	}

	@Override
	public void unregister(INotifyListener listener)
	{
		if (this.mListeners != null)
		{
			this.mListeners.remove(listener);
		}
	}

	@Override
	public void notify(int action, INotifyMessage msg)
	{
		if (this.mListeners != null && this.isEnableNotifications())
		{
			for (INotifyListener listener : this.mListeners)
			{
				listener.notifyListeners(this, action, msg);
			}
		}
	}
	
	public final CArrayList<CGregorianCalendar> getFixtureDates()
	{
		CArrayList<CGregorianCalendar> results = new CArrayList<CGregorianCalendar>();
		
		if (this.mFixtures != null) 
		{
			this.mFixtures.forEach( f -> { if (f.isScheduled() && !results.contains(f.getDateTime())  ) { results.add(f.getDateTime()); } } );			
		}
		
		return results;
	}
	
	public final CArrayList<CGregorianCalendar> getFixtureDates(int clubID)
	{
		CArrayList<CGregorianCalendar> results = new CArrayList<CGregorianCalendar>();
		
		if (this.mFixtures != null) 
		{
			this.mFixtures.forEach( f -> { if (f.isScheduled() && (f.getHomeClubID() == clubID || f.getAwayClubID() == clubID) && !results.contains(f.getDateTime()) ) { results.add(f.getDateTime()); } } );			
		}
		
		return results;
	}
	
	public final CArrayList<CFixture> getFixtures(int clubID)
	{
		CArrayList<CFixture> results = new CArrayList<CFixture>();
		
		if (this.mFixtures != null)
		{
			this.mFixtures.forEach( f -> { if (f.getHomeClubID() == clubID || f.getAwayClubID() == clubID) { results.add(f); } });
		}
		
		return results;
	}
	
	public final CArrayList<CFixture> getFixtureSchedule(int clubID)
	{
		CArrayList<CFixture> results = new CArrayList<CFixture>();
		
		if (this.mFixtures != null)
		{
			this.mFixtures.forEach( f -> { if (f.isScheduled() && (f.getHomeClubID() == clubID || f.getAwayClubID() == clubID)) { results.add(f); } });
		}
		
		return results;
	}
	
	public final CArrayList<CFixture> getFixtures(IDateTime dt)
	{
		CArrayList<CFixture> results = new CArrayList<CFixture>();
		
		if (this.mFixtures != null)
		{
			this.mFixtures.forEach( f -> { if (f.getDateTime() != null && f.isPlayingOn(dt) ) { results.add(f); } } );			
		}
		
		return results;
	}
	
	public final CArrayList<String> getClubNames()
	{
		CHashMap<String, String> names = new CHashMap<String, String>();
		
		if (this.mFixtures != null)
		{
			for(CFixture f : this.mFixtures)
			{
				if (f.getHomeClubName() != null)
				{
					names.add(f.getHomeClubName(), f.getHomeClubName());					
				}
			}			
		}
		
		return new CArrayList<String>(names.keySet());
	}
	
	public final CArrayList<CFixture> getInvalidFixtures()
	{
		CArrayList<CFixture> results = new CArrayList<CFixture>();
		
		if (this.mFixtures != null)
		{
			this.mFixtures.forEach( f -> { if ( f.getStatus() != CFixture.FXT_STATE_VALID) { results.add(f); } } );			
		}
		
		return results;
	}
	
	public final CArrayList<CClub> getClubs(int regionID, int countryID)
	{
		CArrayList<CClub> clubs = new CArrayList<CClub>();
		
		for(String clubnm : this.getClubNames())
		{
			try
			{
				int id = this.getIDFromName(clubnm);
				
				clubs.add( new CClub(clubnm, id, regionID, countryID) );
			} 
			catch (CKeyNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		return clubs;
	}
	
	public final CArrayList<String> getClubsNotPlayingOn(IDateTime dt)
	{
		CArrayList<String> results = this.getClubNames();
		
		if (this.mFixtures != null)
		{
			CArrayList<CFixture> fixtures = this.getFixtures(dt);
						
			fixtures.forEach( fxt -> { results.remove(fxt.getHomeClubName()); results.remove(fxt.getAwayClubName()); } );
		}
		
		return results;
	}

	public final CArrayList<CFixture> getFixtures()
	{
		return this.mFixtures;
	}

	public final void setFixtures(CArrayList<CFixture> fixtures)
	{
		this.mFixtures = fixtures;
	}

	public final int getStatus()
	{
		return this.mStatus;
	}

	public final void setStatus(int status)
	{
		this.mStatus = status;
	}
	
	public final boolean isEnableNotifications()
	{
		return CApplication.isBitSet(CSchedule.BIT_ENABLE_NOTIFY, this.mStatus);		
	}
	
	public final void setEnableNotifications(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CSchedule.BIT_ENABLE_NOTIFY, this.mStatus) : CApplication.clearBit(CSchedule.BIT_ENABLE_NOTIFY, this.mStatus);
	}
	
	public final boolean isModified()
	{
		return CApplication.isBitSet(CSchedule.BIT_MODIFIED, this.mStatus);		
	}
	
	public final void setIsModified(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CSchedule.BIT_MODIFIED, this.mStatus) : CApplication.clearBit(CSchedule.BIT_MODIFIED, this.mStatus);
	}

}
