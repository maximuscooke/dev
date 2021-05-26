package com.maximuscooke.app.soccerboss;

import java.util.List;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CTextTranslator;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CCountry extends CGameObject implements IRegionID, IStatus
{
	private static final long serialVersionUID = 8138683525840545587L;
	
	public static final short LEVEL_MIN=1;
	public static final short LEVEL_MAX=10;
	
	public static final int ALBANIA_ID = 10;
	public static final int ARGENTINA_ID = 20;
	public static final int ANDORRA_ID = 30;
	public static final int AUSTRIA_ID = 40;
	public static final int BELGIUM_ID = 50;
	public static final int BELARUS_ID = 60;
	public static final int BRAZIL_ID = 70;
	public static final int BULGARIA_ID = 80;
	public static final int CHILE_ID = 90;
	public static final int CROATIA_ID = 95;
	public static final int CZECH_REPUBLIC_ID = 96;
	public static final int ENGLAND_ID = 100;
	public static final int DENMARK_ID = 105;
	public static final int FRANCE_ID = 110;	
	public static final int GERMANY_ID = 120;	
	public static final int ICELAND_ID = 130;	
	public static final int IRELAND = 140;
	public static final int ITALY_ID = 150;	
	public static final int MEXICO_ID = 160;
	public static final int NETHERLANDS_ID = 170;
	public static final int NORTHERN_IRELAND_ID = 180;
	public static final int NORWAY_ID = 190;
	public static final int PORTUGAL_ID = 200;	
	public static final int SCOTLAND_ID = 210;
	public static final int SPAIN_ID = 220;
	public static final int SWEDEN_ID = 230;
	public static final int SWITZERLAND_ID = 240;
	public static final int URUGUAY_ID = 250;
	public static final int WALES_ID = 260;	
	
	public static final byte IS_DEFAULT_BIT = 0;
	public static final byte IS_EDITABLE_BIT = 1;
	
	private static final int EXPORT_VERSION = 1;
	
	private String mFirstnameFile=null;
	private String mSurnameFile=null;
	private String mClubNameFile=null;
	private String mClubExtFile=null;
		
	private int mRegionID=-1;
	private int mStatus = 0;
	private int mMaxClubs=0;
	private int mPlayerCount=0;
	private int mSoccerLevel=0;
	private int mLevel=0;

	public CCountry()
	{
	}
	
	public CCountry(String name, int id, int regionID, String image)
	{
		super(name, id);
		
		this.setRegionID(regionID);
		
		this.setImageName16(image);
	}
	
	public static CCountry createInstance(String name, int id, int regionID, String image16)
	{
		return createInstance(name, id, regionID, 0, image16, null, null, null, null);
	}
	
	public static CCountry createInstance(String name, int id, int regionID, int maxClubs, String image16, String clubnmfile, String clubextfile, String firstnmfile, String surnmfile)
	{
		CCountry c = new CCountry(CTextTranslator.translate(name), id, regionID, image16);
		
		c.setFirstnameFile(firstnmfile);
		
		c.setSurnameFile(surnmfile);
		
		c.setClubNameFile(clubnmfile);
		
		c.setClubExtFile(clubextfile);
		
		c.setMaxClubs(maxClubs);
		
		return c; 
	}

//	public abstract void initCompetitions();

	public void init()
	{

//		if (CExportFile.exportFileExists(this.getExportFilename()))
//		{
//
//			try
//			{
//				CExportFile f = this.loadExport();
//
//				initClubs(f);
//
////				initCompetitions();
//
////				initFixtures();
//
//			} 
//			catch (Exception e)
//			{
//				e.printStackTrace();
//
//				System.exit(-1);
//			}
//		}
	}

	protected void initFixtures()
	{

//		for(CCompetition c : this.getCompetitionList()) {
//			c.createFixtures();
//		}
	}

	private String getExportFilename()
	{
		return this.getName().replace(' ', '_');
	}
	

	public static CArrayList<CCountry> getDefaultCountries()
	{
		CArrayList<CCountry> list = new CArrayList<CCountry>(128);
								
		list.add( CCountry.createInstance("England", CCountry.ENGLAND_ID, CRegion.EUROPE, 150, "England.png", "England.cnm", "England.ext", null, null ) );
		
		list.add( CCountry.createInstance("Scotland", CCountry.SCOTLAND_ID, CRegion.EUROPE, "Scotland.png") );
		
		list.add( CCountry.createInstance("France", CCountry.FRANCE_ID, CRegion.EUROPE, "France.png") );
		
		list.add( CCountry.createInstance("Germany", CCountry.GERMANY_ID, CRegion.EUROPE, "Germany.png") );
		
		list.add( CCountry.createInstance("Italy", CCountry.ITALY_ID, CRegion.EUROPE, "Italy.png") );
		
		list.add( CCountry.createInstance("Netherlands", CCountry.NETHERLANDS_ID, CRegion.EUROPE, "Netherlands.png") );
		
		list.add( CCountry.createInstance("Spain", CCountry.SPAIN_ID, CRegion.EUROPE, "Spain.png") );
		
		list.add( CCountry.createInstance("Sweden", CCountry.SWEDEN_ID, CRegion.EUROPE, "Sweden.png") );
		
		list.add( CCountry.createInstance("Wales", CCountry.WALES_ID, CRegion.EUROPE, "Wales.png") );
		
		list.add( CCountry.createInstance("Belgium", CCountry.BELGIUM_ID, CRegion.EUROPE, "Belgium.png") );
		
		list.add( CCountry.createInstance("Andorra", CCountry.ANDORRA_ID, CRegion.EUROPE, "Andorra.png") );
		
		list.add( CCountry.createInstance("Albania", CCountry.ALBANIA_ID, CRegion.EUROPE, "Albania.png") );
		
		list.add( CCountry.createInstance("Austria", CCountry.AUSTRIA_ID, CRegion.EUROPE, "Austria.png") );
		
		list.add( CCountry.createInstance("Switzerland", CCountry.SWITZERLAND_ID, CRegion.EUROPE, "Switzerland.png") );
		
		list.add( CCountry.createInstance("Ireland", CCountry.IRELAND, CRegion.EUROPE, "Ireland.png") );
		
		list.add( CCountry.createInstance("Norway", CCountry.NORWAY_ID, CRegion.EUROPE, "Norway.png") );
		
		list.add( CCountry.createInstance("Belarus", CCountry.BELARUS_ID, CRegion.EUROPE, "Belarus.png") );
		
		list.add( CCountry.createInstance("Iceland", CCountry.ICELAND_ID, CRegion.EUROPE, "Iceland.png") );
		
		list.add( CCountry.createInstance("Portugal", CCountry.PORTUGAL_ID, CRegion.EUROPE, "Portugal.png") );
		
		list.add( CCountry.createInstance("Bulgaria", CCountry.BULGARIA_ID, CRegion.EUROPE, "Bulgaria.png") );
		
		list.add( CCountry.createInstance("Denmark", CCountry.DENMARK_ID, CRegion.EUROPE, "Denmark.png") );
		
		list.add( CCountry.createInstance("Croatia", CCountry.CROATIA_ID, CRegion.EUROPE, "Croatia.png") );
		
		list.add( CCountry.createInstance("Northern Ireland", CCountry.NORTHERN_IRELAND_ID, CRegion.EUROPE, "NorthernIreland.png") );
		
		list.add( CCountry.createInstance("Czech Republic", CCountry.CZECH_REPUBLIC_ID, CRegion.EUROPE, "CzechRepublic.png") );

		list.add( CCountry.createInstance("Argentina", CCountry.ARGENTINA_ID, CRegion.SOUTH_AMERICA, "Argentina.png") );
		
		list.add( CCountry.createInstance("Brazil", CCountry.BRAZIL_ID, CRegion.SOUTH_AMERICA, "Brazil.png") );
		
		list.add( CCountry.createInstance("Mexico", CCountry.MEXICO_ID, CRegion.SOUTH_AMERICA, "Mexico.png") );
		
		list.add( CCountry.createInstance("Chile", CCountry.CHILE_ID, CRegion.SOUTH_AMERICA, "Chile.png") );
		
		list.add( CCountry.createInstance("Uruguay", CCountry.URUGUAY_ID, CRegion.SOUTH_AMERICA, "Uruguay.png") );
		
		
		
		for(CCountry c : list)
		{
			c.setIsDefault(true);
		}

		return list;
	}
	
	@Override
	public String exportObject(String separator)
	{
//		return IExportObject.toExportString(separator, new String[] { 	Integer.toString(CCountry.EXPORT_VERSION), 
//																				  			Integer.toString(this.getIntegerID()),
//																				  			Integer.toString(this.getRegionID()),
//																				  			Integer.toString(this.getStatus()),
//																				  			Integer.toString(this.getMaxClubs()),
//																				  			CGameObject.exportString( this.getName() ), 
//																				  			CGameObject.exportString(this.getImageName16()),
//																				  			CGameObject.exportString(this.getClubFile()),
//																				  			CGameObject.exportString(this.getPersonnelFile()),
//																				  			
//																					} );
		return null;
	}
	
	@Override
	public void importObject(List<String> values)
	{
//		int version = (int)Integer.parseInt( values.get(0) );
//		
//		if (version == CCountry.EXPORT_VERSION ) 
//		{
//			this.setIntegerID( Integer.parseInt( values.get(1) ) );
//			
//			this.setRegionID( Integer.parseInt( values.get(2) ) );
//			
//			this.setStatus( Integer.parseInt( values.get(3) ) );
//			
//			this.setMaxClubs( Integer.parseInt( values.get(4) ) );
//			
//			this.setName( CGameObject.importString( values.get(5) ) );
//			
//			this.setImageName16( CGameObject.importString( values.get(6) ) );
//			
//			this.setClubFile( CGameObject.importString( values.get(7) ) );
//			
//			this.setPersonnelFile( CGameObject.importString( values.get(8) ) );
//		}
	}
	
	
	public final boolean isDefault()
	{
		return CApplication.isBitSet(CCountry.IS_DEFAULT_BIT, this.mStatus);
	}
	
	public final void setIsDefault(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CCountry.IS_DEFAULT_BIT, this.mStatus) : CApplication.clearBit(CCountry.IS_DEFAULT_BIT, this.mStatus);
	}
	
	public final boolean isEditable()
	{
		return CApplication.isBitSet(CCountry.IS_EDITABLE_BIT, this.mStatus);
	}
	
	public final void setIsEditable(boolean bEnable)
	{
		this.mStatus = bEnable ? CApplication.setBit(CCountry.IS_EDITABLE_BIT, this.mStatus) : CApplication.clearBit(CCountry.IS_EDITABLE_BIT, this.mStatus);
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
	
	@Override
	public final int getRegionID()
	{
		return this.mRegionID;
	}
	
	public final void setRegionID(int id)
	{
		this.mRegionID = id;
	}

	public final int getMaxClubs()
	{
		return this.mMaxClubs;
	}

	public final void setMaxClubs(int max)
	{
		this.mMaxClubs = max;
	}

	public final int getPlayerCount()
	{
		return this.mPlayerCount;
	}

	public final void setPlayerCount(int count)
	{
		this.mPlayerCount = count;
	}

	public final int getSoccerLevel()
	{
		return this.mSoccerLevel;
	}

	public final void setSoccerLevel(int level)
	{
		this.mSoccerLevel = Math.max(1, Math.min(10, level));
	}

	public final String getFirstnameFile()
	{
		return this.mFirstnameFile;
	}

	public final void setFirstnameFile(String fn)
	{
		this.mFirstnameFile = fn;
	}

	public final String getSurnameFile()
	{
		return this.mSurnameFile;
	}

	public final void setSurnameFile(String fn)
	{
		this.mSurnameFile = fn;
	}

	public final String getClubNameFile()
	{
		return this.mClubNameFile;
	}

	public final void setClubNameFile(String fn)
	{
		this.mClubNameFile = fn;
	}

	public final String getClubExtFile()
	{
		return this.mClubExtFile;
	}

	public final void setClubExtFile(String fn)
	{
		this.mClubExtFile = fn;
	}

	public final int getLevel()
	{
		return this.mLevel;
	}

	public final void setLevel(int lvl)
	{
		this.mLevel = lvl;
	}
}
