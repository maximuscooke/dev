package com.maximuscooke.app.soccerboss;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CConfigFile;
import com.maximuscooke.lib.common.CFile;
import com.maximuscooke.lib.common.CSerialize;
import com.maximuscooke.lib.common.CTextFile;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CSoccerBoss extends CApplication
{
	private static CDatabase mDatabase;
	private static CDbConfigFile mConfigFile = null;
	
	public static final String CLUB_EXT = "club";
	public static final String CLUB_MARKER = "<CLUB>";
	public static final String EXT_MARKER = "<EXT>";
	public static final String FORENAME_MARKER = "<FORENAME>";
	public static final String SURNAME_MARKER = "<SURNAME>";
	public static final String PERSONNEL_EXT = "per";
	
	public static final String EXPORT_SEPARATOR = "|";
	
	public static final String DIR_SEPARATOR;
	public static final String INSTALL_DIR;
	public static final String DATA_DIR;
	public static final String DB_DIR;
	public static final String SCH_TMPL_DIR;
	public static final String EXPORT_DIR;
	public static final String IMAGES_DIR;
	public static final String USER_IMAGES_DIR;
	public static final String RES_DIR;
	
	public static final String COUNTRY_DIR;
	public static final String SETUP_DIR;

	public static final String DB_EXT = "db";
	public static final String DB_EXPORT = "dxp";
	public static final String CLUB_EXPORT = "cxp";
	public static final String PERSONNEL_EXPORT = "pxp";
	public static final String SCH_TMPL_EXPORT = "sxp";
	public static final String SCH_TMPL_EXT = "tmpl";
	
	public static final String FIRSTNAME_EXT = "fnm";
	public static final String SURNAME_EXT = "snm";
	public static final String CLUBNAME_EXT = "cnm";
	public static final String CLUBEXT_EXT = "ext";


	public static final int DB_FORMAT = CSerialize.SERIALIZE_FORMAT_XML;
	
	static
	{
		if (!CFile.exists(CFile.getWorkingDirectory(), CDbConfigFile.CONFIG_FILE_NAME))
		{
			mConfigFile = new CDbConfigFile();
			
			mConfigFile.setupDefaults();
					
		}
		else
		{
			try
			{
				mConfigFile = (CDbConfigFile)CConfigFile.load(CFile.getWorkingDirectory(), CDbConfigFile.CONFIG_FILE_NAME);
			} 
			catch (Exception e)
			{
				mConfigFile = new CDbConfigFile();
				
				mConfigFile.setupDefaults();
			}
		}
		
		RES_DIR = "/com/maximuscooke/app/soccerboss/editor/res/";

		DIR_SEPARATOR =  mConfigFile.getParam(CDbConfigFile.PATH_SEPARATOR_KEY);
		
		INSTALL_DIR = mConfigFile.getParam(CDbConfigFile.HOME_DIR_KEY);
		
		DATA_DIR = INSTALL_DIR + DIR_SEPARATOR + "data"+ DIR_SEPARATOR;
		
		SETUP_DIR = DATA_DIR + "setup" + DIR_SEPARATOR;
				
		DB_DIR = DATA_DIR + "db" + DIR_SEPARATOR;

		COUNTRY_DIR = DATA_DIR  + "country"+ DIR_SEPARATOR;
						
		SCH_TMPL_DIR = DATA_DIR + "templates"+ DIR_SEPARATOR;
		
		EXPORT_DIR = DATA_DIR + "export"+ DIR_SEPARATOR;
		
		IMAGES_DIR = DATA_DIR + "images"+ DIR_SEPARATOR;
		
		USER_IMAGES_DIR = DATA_DIR + "user"+ DIR_SEPARATOR;
		
		System.out.println("INSTALL_DIR=" + INSTALL_DIR);
		
		System.out.println("DATA_DIR=" + DATA_DIR);
		
		System.out.println("SETUP_DIR=" + SETUP_DIR);
		
		System.out.println("DB_DIR=" + DB_DIR);
				
		System.out.println("COUNTRY_DIR=" + COUNTRY_DIR);
				
		System.out.println("EXPORT_DIR=" + EXPORT_DIR);
				
		System.out.println("IMAGES_DIR=" + IMAGES_DIR);
		
		System.out.println("SCH_TMPL_DIR=" + SCH_TMPL_DIR);
		
		System.out.println("RES_DIR=" + RES_DIR);
				
		init();
	}

	public static void init()
	{
		checkDirectoryStructure();
	}

	public static void run()
	{
	}

	public static CDatabase createEmptyDB(String name)
	{

//		setDB(CDatabase.createEmptyDB(name));

		return mDatabase;
	}

	public  static CDatabase createDefaultDB(String name)
	{

//		setDB(CDatabase.createEmptyDB(name));

//		mDatabase.setupDefaults();

		return mDatabase;
	}

	public synchronized static CDatabase openDB(String filename) throws FileNotFoundException, ClassNotFoundException, IOException
	{
	//	setDB(CDatabase.openDB(DB_DIR, filename));

		return mDatabase;
	}
	
	public static CTextFile loadTextFile(String dir, String fName) throws Exception
	{
		if (dir != null && fName != null && CFile.exists(dir, fName))
		{
			CTextFile tf = new CTextFile(dir, fName);
			
			tf.read();
			
			return tf;
		}
		
		return null;
	}

	public static void checkDirectoryStructure()
	{
//		CFile.createDirectories(BASE_DIR, DB_DIR, RES_DIR, SCH_TMPL_DIR);
	}

	/**
	 * Return CDatabase object
	 * 
	 * @return database object
	 */
	public synchronized static CDatabase getDB()
	{
		return mDatabase;
	}

	public synchronized static void setDB(CDatabase db)
	{
		mDatabase = db;
	}

	/**
	 * Checks if a database exists
	 * 
	 * @param dbName name of database to check for.
	 * @return true if database exists, else false.
	 */
	public static boolean doesDBExist(String dbName)
	{

		boolean bExists = false;

		if (dbName != null)
		{
			StringBuilder sb = new StringBuilder(CSoccerBoss.DB_DIR);

			if (!dbName.endsWith("." + CSoccerBoss.DB_EXT))
			{
				sb.append(dbName);

				sb.append("." + CSoccerBoss.DB_EXT);
			}

			bExists = CFile.exists(sb.toString());
		}

		return bExists;
	}

	/**
	 * Indicates whether the database has been modified
	 * 
	 * @return true id database has been modified, else false
	 */
	public synchronized static boolean isModified()
	{
		if (mDatabase != null)
		{
			return mDatabase.isModified();
		}
		return false;
	}

	/**
	 * Returns a list of existing database names
	 */
	public synchronized static CArrayList<String> getSavedGames()
	{
		return getSavedGames(false);
	}

	/**
	 * Returns a list of existing database names
	 * 
	 * @param bStripExt if true strips extension from file names
	 * @return
	 */
	public synchronized static CArrayList<String> getSavedGames(boolean bStripExt)
	{
		CArrayList<String> results = CFile.getListOfFilePaths(DB_DIR, "." + DB_EXT, true);

		if (bStripExt && results != null && results.size() > 0)
		{
			CArrayList<String> tmp = new CArrayList<String>(results.size());

			for (String s : results)
			{
				tmp.add(CFile.removeExtension(s));
			}

			Collections.sort(tmp);

			results = tmp;
		}

		return results;
	}

	/**
	 * Indicates whether database is available
	 * 
	 * @return true if database is available/created
	 */
	public synchronized static boolean isDBAvailable()
	{
		return (mDatabase != null);
	}

}
