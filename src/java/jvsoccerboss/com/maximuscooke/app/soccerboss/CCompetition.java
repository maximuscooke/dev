package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.exceptions.CDuplicateKeyException;

public abstract class CCompetition extends CGameObject implements ICountryID, IStatus
{
	public static final int DEFAULT_LGE_POINTS_WIN = 3;
	public static final int DEFAULT_LGE_POINTS_DRAW = 1;
	public static final int DEFAULT_LGE_NUM_TEAMS = 20;
	public static final int DEFAULT_LGE_MAX_TEAMS = 24;
	public static final int DEFAULT_LGE_MIN_TEAMS = 4;
	public static final int DEFAULT_REVERSE_FIXTURE_GAP_MIN = 8;
	public static final int DEFAULT_REVERSE_FIXTURE_GAP_MAX = 100;
	public static final int DEFAULT_HOME_AWAY_CONSECUTIVE_MIN=2;
	public static final int DEFAULT_HOME_AWAY_CONSECUTIVE_MAX=100;
	public static final int DEFAULT_FIXTURE_GAP_MIN = 1;
	public static final int DEFAULT_FIXTURE_GAP_MAX = 7;
	
	public static final byte BIT_SUPPORT_VAR = 0;
	public static final byte BIT_SUPPORT_GOALLINE_TECH = 1;
	public static final byte BIT_SUPPORT_REPLAYS = 2;

	public static final int TYPE_NATIONAL_LEAGUE = (1<<0);
	public static final int TYPE_NATIONAL_CUP = (1<<1);
	public static final int TYPE_NATIONAL_PLAYOFF = (1<<2);

	private static final long serialVersionUID = 473734129740974843L;

	private CArrayList<Integer> mClubIds = new CArrayList<Integer>();
	private String mScheduleTemplate=null;

	private int mClubCount = 20;
	private int mCountryID = -1;

	private int mStatus=0;

	public CCompetition()
	{
	}

	public CCompetition(String name, int countryId, int id, int clubCount)
	{
		super(name, id);
		
		this.setClubCount(clubCount);
		
		this.setCountryID(countryId);
	}

	public static CArrayList<CCompetition> getDefaultCompetitions()
	{
		CArrayList<CCompetition> list = new CArrayList<CCompetition>(512);
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_LEAGUE, "Division 1", CCountry.ENGLAND_ID, -1, 20, 1, 3, 1, 0, 0, 3,"Eng20" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_LEAGUE, "Division 2", CCountry.ENGLAND_ID, -1, 24, 2, 3, 1, 0, 3, 3, "Eng24" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_LEAGUE, "Division 3", CCountry.ENGLAND_ID, -1, 24, 3, 3, 1, 0, 3, 3, "Eng24" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_LEAGUE, "Division 4", CCountry.ENGLAND_ID, -1, 24, 4, 3, 1, 0,3, 3, "Eng24" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_LEAGUE, "Non League", CCountry.ENGLAND_ID, -1, 24, 5, 3, 1, 0, 3, 1, "Eng24" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_CUP, "Soccer Association Cup", CCountry.ENGLAND_ID, -1, 128, "Eng20" + "." + CSoccerBoss.SCH_TMPL_EXT) );
		
		list.add( CCompetition.createInstance(CCompetition.TYPE_NATIONAL_CUP, "Soccer League Cup", CCountry.ENGLAND_ID, -1, 128, "Eng20" + "." + CSoccerBoss.SCH_TMPL_EXT) );	
		
		return list;
	}
	
	public static CCompetition createInstance(int type, String name, int countryId, int id, int maxClubs, String schTmp)
	{
		return createInstance(type, name, countryId, id, maxClubs, -1, -1, -1, -1, -1, -1, schTmp);
	}
	
	public static CCompetition createInstance(int type, 
															String name, 
															int countryId, 
															int id, 
															int maxClubs, 
															int precedenceOrder, 
															int win, 
															int draw, 
															int defeat, 
															int promotionCount, 
															int relegationCount, 
															String schTmp)
	{
		if (type == CCompetition.TYPE_NATIONAL_LEAGUE)
		{
			CNationalLeague lg = new CNationalLeague(name, countryId, id, maxClubs, precedenceOrder);
			
			lg.setPointsForWin(win);
			
			lg.setPointsForDraw(draw);
			
			lg.setPointsForDefeat(defeat);
			
			lg.setScheduleTemplate(schTmp);
			
			lg.setPromotionCount(promotionCount);
			
			lg.setRelegationCount(relegationCount);
			
			return lg;
		}
		else if (type == CCompetition.TYPE_NATIONAL_CUP)
		{
			CNationalCup cup = new CNationalCup(name, countryId, id, maxClubs);
			
			cup.setScheduleTemplate(schTmp);
			
			return cup;
		}
		
		return null;
	}

	public abstract int getType();

	public void addClub(CClub c) throws CDuplicateKeyException
	{
		if (this.mClubIds.contains(c.getIntegerID()))
		{
			throw new CDuplicateKeyException(String.format("Duplicate Club ID <%s> <%d>, in competition <%s>", c.getName(), (int)c.getIntegerID(), this.getName()));
		}
		
		this.mClubIds.add(c.getIntegerID());
	}

	public final void removeClub(Integer id)
	{
		this.mClubIds.remove(id);
	}

	public final void clear()
	{
		this.mClubIds.clear();
	}

	public final int getClubCount()
	{
		return this.mClubCount;
	}

	public final void setClubCount(int count)
	{
		this.mClubCount = count;
	}

	public final CArrayList<Integer> getClubIds()
	{
		return this.mClubIds;
	}

	public final void setClubIds(CArrayList<Integer> clubs)
	{
		this.mClubIds = clubs;
	}

	@Override
	public final 	int getCountryID()
	{
		return this.mCountryID;
	}

	public final void setCountryID(int id)
	{
		this.mCountryID = id;
	}

	@Override
	public final int getStatus()
	{
		return this.mStatus;
	}

	@Override
	public final void setStatus(int status)
	{
		this.mStatus = status;
	}

	public final String getScheduleTemplate()
	{
		return this.mScheduleTemplate;
	}

	public final void setScheduleTemplate(String tmpl)
	{
		this.mScheduleTemplate = tmpl;
	}

	public final boolean supportVAR()
	{
		return CApplication.isBitSet(CCompetition.BIT_SUPPORT_VAR, this.mStatus);		
	}
	
	public final void setSupportVAR(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CCompetition.BIT_SUPPORT_VAR, this.mStatus) : CApplication.clearBit(CCompetition.BIT_SUPPORT_VAR, this.mStatus);
	}
	
	public final boolean supportGoalLineTech()
	{
		return CApplication.isBitSet(CCompetition.BIT_SUPPORT_GOALLINE_TECH, this.mStatus);		
	}
	
	public final void setSupportGoalLineTech(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CCompetition.BIT_SUPPORT_GOALLINE_TECH, this.mStatus) : CApplication.clearBit(CCompetition.BIT_SUPPORT_GOALLINE_TECH, this.mStatus);
	}
	
	public final boolean supportReplays()
	{
		return CApplication.isBitSet(CCompetition.BIT_SUPPORT_REPLAYS, this.mStatus);		
	}
	
	public final void setSupportReplays(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CCompetition.BIT_SUPPORT_REPLAYS, this.mStatus) : CApplication.clearBit(CCompetition.BIT_SUPPORT_REPLAYS, this.mStatus);
	}
}