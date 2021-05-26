package com.maximuscooke.app.soccerboss;

import java.util.List;

import com.maximuscooke.lib.common.CTextTranslator;
import com.maximuscooke.lib.common.IExportObject;
import com.maximuscooke.lib.common.collections.CArrayList;

public class CRegion extends CGameObject implements IStatus
{
	private static final long serialVersionUID = 2559901983517228531L;

	public static final int EUROPE = 10;
	public static final int SOUTH_AMERICA = 20;
	
	private 	static final int EXPORT_VERSION = 1;
	
	private int mStatus=0;
	
	public CRegion()
	{
	}

	public CRegion(String name, int id)
	{
		this(name, id, "wi0064-16.png");
	}
	
	public CRegion(String name, int id, String image)
	{
		super(name, id);
		
		this.setImageName16(image);
	}
	
	public static CArrayList<CRegion> getDefaultRegions()
	{
		CArrayList<CRegion> list = new CArrayList<CRegion>();
		
		list.add( new CRegion(CTextTranslator.translate("Europe"), CRegion.EUROPE) );
		
		list.add( new CRegion(CTextTranslator.translate("South America"), CRegion.SOUTH_AMERICA) );
		
		return list;
	}

	@Override
	public String exportObject(String separator)
	{
		return IExportObject.toExportString(separator, new String[] { Integer.toString(CRegion.EXPORT_VERSION), 
																				  Integer.toString(this.getIntegerID()), 
																				  CGameObject.exportString( this.getName() ), 
																				  CGameObject.exportString(this.getImageName16()) } );
	}

	@Override
	public void importObject(List<String> values)
	{
		int version = (int)Integer.parseInt( values.get(0) );
		
		if (version == CRegion.EXPORT_VERSION ) 
		{
			this.setIntegerID( Integer.parseInt( values.get(1) ) );
			
			this.setName( CGameObject.importString( values.get(2) ) );
			
			this.setImageName16( CGameObject.importString( values.get(3) ) );
		}
	}

	@Override
	public int getStatus()
	{
		return this.mStatus;
	}

	@Override
	public void setStatus(int status)
	{
		this.mStatus=status;
	}
}
