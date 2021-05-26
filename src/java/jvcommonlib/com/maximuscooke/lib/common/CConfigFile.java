package com.maximuscooke.lib.common;

import com.maximuscooke.lib.common.collections.CHashMap;

public class CConfigFile extends CFile
{
	private static final long serialVersionUID = 545510348921920382L;

	private CHashMap<String, String> mMap = new CHashMap<String, String>();
	
	public CConfigFile()
	{
		this("app.config");
	}

	public CConfigFile(String fName)
	{
		super(null, fName);
	}

	public CConfigFile(String dir, String fName)
	{
		super(dir, fName);
	}
	
	public static CConfigFile load(String dir, String fName) throws Exception
	{
		return (CConfigFile)CSerialize.deSerializeObjects(dir, fName, CSerialize.SERIALIZE_FORMAT_XML);
	}
	
	public void addParam(String key, String o)
	{
		mMap.add(key, o);
	}

	public String getParam(String key)
	{
		return this.mMap.get(key);
	}

	public int getParamAsInteger(String key)
	{
		return (int) Integer.parseInt(this.getParamAsString(key));
	}

	public long getParamAsLong(String key)
	{
		return (long) Long.parseLong(this.getParamAsString(key));
	}

	public String getParamAsString(String key)
	{
		return (String) this.getParam(key);
	}

	public float getParamAsFloat(String key)
	{
		return (float) Float.parseFloat(this.getParamAsString(key));
	}

	public double getParamAsDouble(String key)
	{
		return (double) Double.parseDouble(this.getParamAsString(key));
	}

	public void clear()
	{
		this.mMap.clear();
	}

	public void save() throws Exception
	{
		this.save(this.getDirectory(), this.getFileName());
	}

	public void save(String directory, String fileName) throws Exception
	{
		CSerialize.serializeObjects(directory, fileName, CSerialize.SERIALIZE_FORMAT_XML, this);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("[CONFIG FILE]: ");
		
		if (this.mMap.size() > 0)
		{
			for (String k : this.mMap.keySet())
			{
				sb.append("[");
				sb.append(k);
				sb.append("=");
				sb.append(this.getParam(k));
				sb.append("] ");
			}
		} 
		else
		{
			sb.append("Config file is empty.");
		}

		return sb.toString();
	}

	public final CHashMap<String, String> getMap()
	{
		return mMap;
	}

	public final void setMap(CHashMap<String, String> map)
	{
		this.mMap = map;
	}
}
