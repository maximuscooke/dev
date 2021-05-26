package com.maximuscooke.app.soccerboss;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.swing.ImageIcon;

import com.maximuscooke.app.soccerboss.collections.CMap;
import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CGregorianCalendar;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.CSerialize;
import com.maximuscooke.lib.common.IDisplayString;
import com.maximuscooke.lib.common.IModified;
import com.maximuscooke.lib.common.INotify;
import com.maximuscooke.lib.common.INotifyListener;
import com.maximuscooke.lib.common.INotifyMessage;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.collections.CImageMap;
import com.maximuscooke.lib.common.exceptions.CDuplicateKeyException;
import com.maximuscooke.lib.common.exceptions.CKeyNotFoundException;

public final class CDatabase extends CGameObject implements IModified, IDisplayString, INotify, IBuild
{
	private static final long serialVersionUID = -1214159477822086902L;
					
	public static final byte IS_MODIFIED_BIT = 0;
	public static final byte IS_NOTIFY__ENABLED_BIT = 1;
	
	public static final int DB_SIZE_VERY_SMALL = 1;
	public static final int DB_SIZE_SMALL = 2;
	public static final int DB_SIZE_MEDIUM = 3;
	public static final int DB_SIZE_LARGE = 4;
	public static final int DB_SIZE_VERY_LARGE = 5;
	public static final int DB_SIZE_ULTRA_LARGE = 6;
	
	public static final int ACTION_ADD = 10;
	public static final int ACTION_DELETE = 20;
	public static final int ACTION_UPDATE = 30;
	public static final int ACTION_START_IMPORT_DB = 100;
	public static final int ACTION_END_IMPORT_DB = 110;
	public static final int ACTION_START_EXPORT_DB = 120;
	public static final int ACTION_END_EXPORT_DB = 130;
	public static final int ACTION_BUILD_CLUBS = 140;
	public static final int ACTION_BUILD_NON_PLAYER = 150;
	public static final int ACTION_BUILD_SQUADS = 160;
		
	public static final int DB_TYPE = 1;
	public static final int REGION_TYPE = 10;
	public static final int COUNTRY_TYPE = 20;
	public static final int CLUB_TYPE = 30;
	public static final int PERSON_TYPE = 40;
	public static final int COMPETITION_TYPE = 50;
	public static final int CUP_TYPE = 60;
	public static final int LEAGUE_TYPE = 70;
	
	public static final int REGION_MASK = (1<<0);
	public static final int COUNTRY_MASK = (1<<1);
	public static final int CLUB_MASK = (1<<2);
	public static final int COMPETITION_MASK = (1<<3);
	public static final int PERSON_MASK = (1<<4);
	
	public static final int BUILD_NON_PLAYER_MASK = (1<<0);
	public static final int BUILD_CLUBS_MASK = (1<<1);
	public static final int BUILD_SQUADS_MASK = (1<<2);
	public static final int BUILD_COMPETITIONS_MASK = (1<<3);
		
	public static final int ALL_MASK = -1;
				
	private static transient CImageMap mImageMap = new CImageMap();
	private static CDbConfigFile mConfigFile = null;
	
	private transient INotifyListener mListeners = null;
	
	private CMap<Integer, CRegion> mRegionMap = new CMap<Integer, CRegion>();
	private CMap<Integer, CCountry> mCountryMap = new CMap<Integer, CCountry>(); 
	private CMap<Integer, CClub> mClubMap = new CMap<Integer, CClub>();
	private CMap<Integer, CCompetition> mCompetitionMap = new CMap<Integer, CCompetition>();
	private CMap<Integer, CNonPlayer> mNonPlayerMap = new CMap<Integer, CNonPlayer>();
	
	private CGregorianCalendar mStartDate = new CGregorianCalendar();

	private static int mCount=0;
	private int mStatus = 0;
	private int mDbSize = CDatabase.DB_SIZE_MEDIUM;
	
	static
	{		
		CArrayList<String> files = CFile.getListOfFilePaths(CSoccerBoss.IMAGES_DIR, true);
		
		Collections.sort(files);
		
		for(String fn : files)
		{
			mImageMap.add(fn, CResource.getImageIcon(CSoccerBoss.IMAGES_DIR + fn) );
		}
	}

	public CDatabase()
	{
	}

	public CDatabase(String name)
	{
		this(name, null);
	}
	
	public CDatabase(String name, INotifyListener listener)
	{
		super(name, 0);
		
		this.setImageName16("nd0006-16.png");

		if (listener != null)
		{
			this.register(listener);
			
			this.setNotifyEnabled(true);
		}
		
		this.init();
				
		this.setModified(true);
	}
		
	public static void saveDB(String dir, String fn, CDatabase db) throws Exception
	{
		CSerialize.serializeObjects(dir, fn, CSerialize.SERIALIZE_FORMAT_XML, db);
		
		db.setModified(false);
	}
	
	public static CDatabase openDB(String dir, String fn) throws Exception, ClassNotFoundException, IOException
	{
		CDatabase db = (CDatabase)CSerialize.deSerializeObjects(dir, fn, CSerialize.SERIALIZE_FORMAT_XML);
				
		db.setModified(false);
		
		return db;
	}
	
	public void exportDb(String dir, String fName) throws Exception
	{
		this.notify(CDatabase.ACTION_START_EXPORT_DB, new CDbMsg(CDatabase.ACTION_START_EXPORT_DB, "Exporting Database Beginning") );
		
//		CCollections.doExport(dir, fName, CSoccerBoss.EXPORT_SEPARATOR, "<REGIONS>", this.mRegionMap.values(), IExportObject.EXPORT_OPT_CREATE);
//		
//		CCollections.doExport(dir, fName, CSoccerBoss.EXPORT_SEPARATOR, "<COUNTRIES>", this.mCountryMap.values(), IExportObject.EXPORT_OPT_APPEND);
//		
//		CCollections.doExport(dir, fName, CSoccerBoss.EXPORT_SEPARATOR, "<CLUBS>", this.mClubMap.values(), IExportObject.EXPORT_OPT_APPEND);
		
		this.notify(CDatabase.ACTION_END_EXPORT_DB, new CDbMsg(CDatabase.ACTION_END_EXPORT_DB, "Exporting Database Complete") );
	}
	
	public void importDb(String dir, String fName) throws Exception
	{		
		this.clear();
		
		this.notify(CDatabase.ACTION_START_IMPORT_DB, new CDbMsg(CDatabase.ACTION_START_IMPORT_DB, "Importing Database Beginning") );
				
//		CCollections.doImport(dir, fName, "|", "<REGIONS>", ln -> { List<String> exportList = Arrays.asList(ln.split("\\|")); CRegion rg = new CRegion(); rg.importObject(exportList); try { addRegion( rg ); } catch (CDuplicateKeyException e) { e.printStackTrace(); } } );
//				
//		CCollections.doImport(dir, fName, "|", "<COUNTRIES>", ln -> { List<String> exportList = Arrays.asList(ln.split("\\|")); CCountry c = new CCountry(); c.importObject(exportList); try { addCountry( c ); } catch (CDuplicateKeyException | CKeyNotFoundException e) { e.printStackTrace(); } } );
		
		this.notify(CDatabase.ACTION_END_IMPORT_DB, new CDbMsg(CDatabase.ACTION_END_IMPORT_DB, "Importing Database Complete") );
	}
		
	private final void init()
	{
		clear();
		
		CRegion.getDefaultRegions().forEach( r -> initRegion(r) );
				
		CCountry.getDefaultCountries().forEach( c -> { try { addCountry(c); } catch (CDuplicateKeyException | CKeyNotFoundException e) { e.printStackTrace(); } } );
		
		CCompetition.getDefaultCompetitions().forEach( c -> { try { addCompetition(c); } catch (CDuplicateKeyException e) { e.printStackTrace(); } });
		
		this.build(-1);
	}
	
	private void initRegion(CRegion r)
	{
		try 
		{ 
			addRegion(r); 
		} 
		catch (CDuplicateKeyException e)
		{ 
			e.printStackTrace(); 
		}
	}
	
	@Override
	public final void build(int options)
	{
		if ((CDatabase.BUILD_CLUBS_MASK & options) != 0)
		{
			buildClubs(options);
		}

		if ((CDatabase.BUILD_COMPETITIONS_MASK & options) != 0)
		{
			try
			{
				buildNationalCompetitions(options);
			} 
			catch (CDuplicateKeyException e)
			{
				System.out.println(e);
				
				e.printStackTrace();
			}
		}		
		
		if ((CDatabase.BUILD_NON_PLAYER_MASK & options) != 0)
		{
			buildNonPlayer(options);
		}

		if ((CDatabase.BUILD_SQUADS_MASK & options) != 0)
		{
			buildSquads(options);
		}
		
	}
	
	private void buildNationalCompetitions(int options) throws CDuplicateKeyException
	{
//		for(CCountry country : this.mCountryMap.values())
//		{
//			CArrayList<CClub> clubList = this.getClubsFor(country);
//			
//			clubList.shuffle();
//			
//			CArrayList<CCompetition> compList = this.getCompetitionsFor(country, CCompetition.TYPE_NATIONAL_LEAGUE);
//			
//			compList.shuffle();
//						
//			for(CCompetition comp : compList)
//			{
//				comp.clear();
//				
//				for(int i = 0; i < comp.getClubCount(); i++)
//				{
//					CClub club = clubList.getRandom();
//					
//					comp.addClub(club);
//					
//					clubList.remove(club);
//				}
//			}
//		}
	}
	
	private void buildNonPlayer(int options)
	{
		this.mNonPlayerMap.clear();
		
		this.notify(CDatabase.ACTION_BUILD_NON_PLAYER, new CDbMsg(CDatabase.ACTION_BUILD_NON_PLAYER, "Building Personnel Collection") );
	}
	
	private void buildClubs(int options)
	{
		this.mClubMap.clear();
		
		for(CCountry c : this.mCountryMap.values())
		{
			try
			{				
				CTextFileRandomizer randomizer = new CTextFileRandomizer(CSoccerBoss.SETUP_DIR, c.getClubNameFile(), CSoccerBoss.SETUP_DIR, c.getClubExtFile(), 256, true);

				int count = 0;

				int startID = this.getNextID(this.mClubMap.keySet());

				while (count <= c.getMaxClubs())
				{
					String clubName = randomizer.getRandomName();

					count++;

					this.addClub(new CClub(clubName, startID++, c.getRegionID(), c.getIntegerID()));
				}
			} 
			catch (Exception e)
			{
				System.out.println("Skipping  " + c.getName());
			}
		}
		
		this.notify(CDatabase.ACTION_BUILD_CLUBS, new CDbMsg(CDatabase.ACTION_BUILD_CLUBS, "Building Personnel Collection") );
	}
	
	private void buildSquads(int options)
	{
		this.notify(CDatabase.ACTION_BUILD_SQUADS, new CDbMsg(CDatabase.ACTION_BUILD_SQUADS, "Building Personnel Squads") );
	}

	public final void clear()
	{
		this.clear(CDatabase.ALL_MASK);
	}
	
	public final void clear(int mask)
	{
		if ((mask & CDatabase.REGION_MASK) != 0)
		{
			this.mRegionMap.clear();
		}
		
		if ((mask & CDatabase.COUNTRY_MASK) != 0)
		{
			this.mCountryMap.clear();
		}
		
		if ((mask & CDatabase.CLUB_MASK) != 0)
		{
			this.mClubMap.clear();
		}
		
		if ((mask & CDatabase.COMPETITION_MASK) != 0)
		{
			this.mCompetitionMap.clear();
		}
	}
	
	public final int getNextID(Set<Integer> list)
	{
		CDatabase.mCount = 0;
		
		list.forEach( val -> CDatabase.mCount = Math.max(CDatabase.mCount, val) );
		
		return (int)CDatabase.mCount + 1;
	}
	
//	public final int getNextID(int type)
//	{
//		CDatabase.mCount = 0;
//		
//		getNextID(this.mRegionMap.keySet());
//		
//		if (type == CDatabase.REGION_TYPE)
//		{
//			this.mRegionMap.keySet().forEach( k -> CDatabase.mCount = Math.max(CDatabase.mCount, k) ) ;
//		}
//		else if (type == CDatabase.COUNTRY_TYPE)
//		{
//			this.mCountryMap.keySet().forEach( k -> CDatabase.mCount = Math.max(CDatabase.mCount, k) ) ;
//		}
//		else if (type == CDatabase.CLUB_TYPE)
//		{
//			this.mClubMap.keySet().forEach( k -> CDatabase.mCount = Math.max(CDatabase.mCount, k) ) ;
//		}
//		
//		return (int)CDatabase.mCount + 1;
//	}
			
	public final void register(INotifyListener listener)
	{
		if (this.mListeners == null)
		{
			this.mListeners = listener;
		}
	}

	public final void unregister(INotifyListener listener)
	{
		this.mListeners = null;
	}

	public final void notify(int actionCode, INotifyMessage msg)
	{
		if (this.mListeners != null && this.isNotifyEnabled())
		{
			this.mListeners.notifyListeners(this, actionCode, msg);
		}
	}
	
	public void addRegion(CRegion rg) throws CDuplicateKeyException
	{
		if (rg.getIntegerID() == -1)
		{
			rg.setIntegerID( this.getNextID(this.mRegionMap.keySet()) );
		}
		
		if (this.mRegionMap.containsKey(rg.getIntegerID()))
		{
			throw new CDuplicateKeyException(String.format("Duplicate Region <%s> <%d>", rg.getName(), (int)rg.getIntegerID()));
		}
				
		this.mRegionMap.add(rg.getIntegerID(), rg);
		
		this.setModified(true);
		
		this.notify(CDatabase.ACTION_ADD, new CDbMsg(CDatabase.REGION_TYPE, CDatabase.ACTION_ADD, rg) );
	}
		
	public void deleteRegion(CRegion r)
	{
		this.deleteRegion( r.getIntegerID() );
	}
	
	
	public void deleteRegion(Integer id)
	{
		CRegion r = this.mRegionMap.remove(id);
		
		this.setModified(true);
		
		if (r != null)
		{
			this.notify(CDatabase.ACTION_DELETE, new CDbMsg(CDatabase.REGION_TYPE, CDatabase.ACTION_DELETE, r) );
		}
	}
	
	public void updateRegion(CRegion rg, String name, String imagenm )
	{
		rg.setName(name);
		
		rg.setImageName16(imagenm);
		
		this.setModified(true);
		
		if (rg != null)
		{
			this.notify(CDatabase.ACTION_UPDATE, new CDbMsg(CDatabase.REGION_TYPE, CDatabase.ACTION_UPDATE, rg) );
		}
	}
		
	public void addCountry(CCountry c) throws CDuplicateKeyException, CKeyNotFoundException
	{		
		if (!this.mRegionMap.containsKey( c.getRegionID() ) )
		{
			throw new CKeyNotFoundException(String.format("Region <%d> not found.", (int)c.getRegionID()));
		}

		if (c.getIntegerID() == -1)
		{
			c.setIntegerID( this.getNextID(this.mCountryMap.keySet()) );
		}
		
		if (this.mCountryMap.containsKey(c.getIntegerID()))
		{
			throw new CDuplicateKeyException(String.format("Duplicate Country <%s> <%d>", c.getName(), (int)c.getIntegerID()));
		}
		
		this.mCountryMap.add(c.getIntegerID(), c);
		
		this.setModified(true);
		
		this.notify(CDatabase.ACTION_ADD, new CDbMsg(CDatabase.COUNTRY_TYPE, CDatabase.ACTION_ADD, c) );
	}
		
	public void deleteCountry(CCountry c)
	{
//		this.deleteCountry( c.getIntegerID() );
		this.setModified(true);
	}
	
	public final void addClub(CClub club)
	{
		if (club.getIntegerID() == -1)
		{
			club.setIntegerID( this.getNextID(this.mClubMap.keySet()) );
		}
		
		this.mClubMap.add(club.getIntegerID(), club);
				
		this.setModified(true);
		
		this.notify(CDatabase.ACTION_ADD, new CDbMsg(CDatabase.CLUB_TYPE, CDatabase.ACTION_ADD, club) );
	}
	
	public final void deleteClub(CClub club)
	{
		this.mClubMap.remove(club.getIntegerID());
		
		this.setModified(true);
	}
	
	public final CClub getClub(Integer id)
	{
		return this.mClubMap.get(id);
	}
	
	public final void addCompetition(CCompetition comp) throws CDuplicateKeyException
	{
		if (comp.getIntegerID() == -1)
		{
			comp.setIntegerID( this.getNextID(this.mCompetitionMap.keySet()) );
		}

		if (this.mCompetitionMap.containsKey(comp.getIntegerID()))
		{
			throw new CDuplicateKeyException(String.format("Duplicate Competition <%s> <%d>", comp.getName(), (int)comp.getIntegerID()));
		}
				
		this.mCompetitionMap.add(comp.getIntegerID(), comp);
				
		this.setModified(true);
		
		this.notify(CDatabase.ACTION_ADD, new CDbMsg(CDatabase.COMPETITION_TYPE, CDatabase.ACTION_ADD, comp) );

	}
	
	@Override
	public String toDisplayString(int style)
	{
		return this.getName();
	}

	@Override
	public String toString()
	{
		return this.getName();
	}

	public final void setStatus(int status)
	{
		this.mStatus = status;
	}
	
	public final int getStatus()
	{
		return this.mStatus;
	}
	
	public final boolean isNotifyEnabled()
	{
		return CApplication.isBitSet(CDatabase.IS_NOTIFY__ENABLED_BIT, this.mStatus);
	}
	
	public final void setNotifyEnabled(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CDatabase.IS_NOTIFY__ENABLED_BIT, this.mStatus) : CApplication.clearBit(CDatabase.IS_NOTIFY__ENABLED_BIT, this.mStatus);
	}

	@Override
	public final boolean isModified()
	{
		return CApplication.isBitSet(CDatabase.IS_MODIFIED_BIT, this.mStatus);
	}
		
	public final void setModified(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CDatabase.IS_MODIFIED_BIT, this.mStatus) : CApplication.clearBit(CDatabase.IS_MODIFIED_BIT, this.mStatus);
	}
	
	public CArrayList<CClub> getClubsFor(CCountry country)
	{
		CArrayList<CClub> results = new CArrayList<CClub>();
		
		this.mClubMap.values().forEach( club -> { if (club.getCountryID() == country.getIntegerID()) { results.add(club); } } );
		
		return results;
	}
	
	public CArrayList<CCompetition> getCompetitionsFor(CCountry country)
	{
		return this.getCompetitionsFor(country, -1);
	}
	
	public CArrayList<CCompetition> getCompetitionsFor(CCountry country, int mask)
	{
		CArrayList<CCompetition> results = new CArrayList<CCompetition>();
		
//		this.mCompetitionMap.values().forEach( comp -> { if (comp.getCountryID() == country.getIntegerID()) { results.add(comp); } } );
		
		for(CCompetition comp : this.mCompetitionMap.values())
		{
			if (comp.getCountryID() == country.getIntegerID())
			{
				if ((mask & CCompetition.TYPE_NATIONAL_LEAGUE) != 0 && comp.getType() == CCompetition.TYPE_NATIONAL_LEAGUE)
				{
					results.add(comp);
				}
				else if ((mask & CCompetition.TYPE_NATIONAL_CUP) != 0 && comp.getType() == CCompetition.TYPE_NATIONAL_CUP)
				{
					results.add(comp);
				}
			}
		}
		
		return results;
	}

	public final CCountry getCountry(int id)
	{		
		return this.mCountryMap.get(id);
	}
		
	public final CRegion getRegion(int id)
	{
		return this.mRegionMap.get(id);
	}

	public final CMap<Integer, CRegion> getRegionMap()
	{
		return this.mRegionMap;
	}

	public final void setRegionMap(CMap<Integer, CRegion> map)
	{
		this.mRegionMap = map;
	}

	public final CMap<Integer, CCountry> getCountryMap()
	{
		return this.mCountryMap;
	}

	public final void setCountryMap(CMap<Integer, CCountry> map)
	{
		this.mCountryMap = map;
	}

	public final CMap<Integer, CClub> getClubMap()
	{
		return this.mClubMap;
	}

	public final void setClubMap(CMap<Integer, CClub> map)
	{
		this.mClubMap = map;
	}
	
	public static CImageMap getImageMap()
	{
		return mImageMap;
	}
	
	public static ImageIcon getImage(String key)
	{
		return mImageMap.get(key);
	}
	
	public static CDbConfigFile getConfigFile()
	{
		return mConfigFile;
	}
	
	public final int getDbSize()
	{
		return this.mDbSize;
	}

	public final void setDbSize(int sz)
	{
		this.mDbSize = sz;
	}

	public final CGregorianCalendar getStartDate()
	{
		return this.mStartDate;
	}

	public final void setStartDate(CGregorianCalendar dt)
	{
		this.mStartDate = dt;
	}

	public final CMap<Integer, CCompetition> getCompetitionMap()
	{
		return this.mCompetitionMap;
	}

	public final void setCompetitionMap(CMap<Integer, CCompetition> map)
	{
		this.mCompetitionMap = map;
	}

	public final CMap<Integer, CNonPlayer> getNonPlayerMap()
	{
		return this.mNonPlayerMap;
	}

	public final void setNonPlayerMap(CMap<Integer, CNonPlayer> map)
	{
		this.mNonPlayerMap = map;
	}
}
