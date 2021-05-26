package com.maximuscooke.app.soccerboss;

import com.maximuscooke.lib.common.CConfigFile;
import com.maximuscooke.lib.common.CFile;

public class CDbConfigFile extends CConfigFile
{
	private static final long serialVersionUID = -5181463861061673213L;
	
	public static final String CONFIG_FILE_NAME = "db.config";
	
	public static final String RES_DIR_KEY = "RES_DIR";
	public static final String PATH_SEPARATOR_KEY = "PATH_SEPARATOR";
	public static final String HOME_DIR_KEY = "HOME_DIR";
	public static final String COUNTRY_DIR_KEY = "COUNTRY_DIR_KEY";
	public static final String COUNTRY_CLUB_DIR_KEY = "COUNTRY_CLUB_DIR_KEY";
	public static final String COUNTRY_PERSONNEL_DIR_KEY = "COUNTRY_PERSONNEL_DIR_KEY";

	public CDbConfigFile()
	{
		super(CFile.getWorkingDirectory(), CDbConfigFile.CONFIG_FILE_NAME);
	}
	
	public void setupDefaults()
	{
		this.addParam(CDbConfigFile.HOME_DIR_KEY, CFile.getWorkingDirectory());
		
		this.addParam(CDbConfigFile.RES_DIR_KEY, "res");
		
		this.addParam(CDbConfigFile.PATH_SEPARATOR_KEY, "/");
		
		this.addParam(CDbConfigFile.COUNTRY_DIR_KEY, "country");
		
		this.addParam(CDbConfigFile.COUNTRY_CLUB_DIR_KEY, "club");
		
		this.addParam(CDbConfigFile.COUNTRY_PERSONNEL_DIR_KEY, "personnel");
		
		try
		{
			this.save();
		} 
		catch (Exception e)
		{			
			e.printStackTrace();
		}
	}
}
