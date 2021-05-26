package com.maximuscooke.app.soccerboss.collections;

import com.maximuscooke.app.soccerboss.CCompetitionAttribute;

public class CCompetitionAttributeMap extends CMap<Integer, CCompetitionAttribute>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5451135048855612159L;

	public CCompetitionAttributeMap()
	{
	}

	/**
	 * Add competition attribute to map
	 * 
	 * @param CCompetitionAttribute competition attribute to add
	 */
	public void add(CCompetitionAttribute attr)
	{

		attr.setIntegerID(this.getNextID());

		this.add(attr.getIntegerID(), attr);
	}

}
