package com.maximuscooke.lib.common;

import java.util.List;

import com.maximuscooke.lib.common.collections.CArrayList;

public interface IExportObject
{
	public static final int EXPORT_OPT_CREATE = (1<<0);
	public static final int EXPORT_OPT_APPEND = (1<<1);

	String exportObject(String separator);
	void importObject(List<String> values);
	
	public static String toExportString(String separator, String[] values)
	{		
		return toExportString(separator, new CArrayList<String>(values));
	}

	public static String toExportString(String separator, List<String> list)
	{
		StringBuilder sb = new StringBuilder(256);
		
		int i;
		for(i = 0; i < list.size() - 1; i++)
		{
			sb.append( list.get(i) );
			
			sb.append(separator);
		}
		
		sb.append(list.get(i));
		
		return sb.toString();
	}
}
