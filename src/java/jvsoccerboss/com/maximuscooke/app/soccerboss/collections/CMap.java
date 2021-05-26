package com.maximuscooke.app.soccerboss.collections;

import java.util.Map;

import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.IIntegerID;
import com.maximuscooke.lib.common.collections.CHashMap;

public class CMap<K, T extends IIntegerID> extends CHashMap<K, T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 788192155598696524L;

	public CMap()
	{
	}

	public CMap(int capacity)
	{
		super(capacity);
	}

	public CMap(int capacity, float loadFactor)
	{
		super(capacity, loadFactor);
	}

	public CMap(Map<? extends K, ? extends T> m)
	{
		super(m);
	}

	/**
	 * Returns the next available ID to use
	 * 
	 * @return Integer ID
	 */
	public int getNextID()
	{
		return CCollections.getNextIntegerID(0, this.values());
	}
}
