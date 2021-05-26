package com.maximuscooke.lib.common.collections;

import java.util.Map;

import javax.swing.ImageIcon;

public class CImageMap extends CHashMap<String, ImageIcon>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 378007743049468841L;

	public CImageMap()
	{
	}

	public CImageMap(int capacity)
	{
		super(capacity);
	}

	public CImageMap(Map<String, ImageIcon> m)
	{
		super(m);
	}
}
