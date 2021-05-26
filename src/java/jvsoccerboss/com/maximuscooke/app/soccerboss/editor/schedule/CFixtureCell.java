package com.maximuscooke.app.soccerboss.editor.schedule;

import com.maximuscooke.app.soccerboss.CFixture;

public class CFixtureCell extends CTableCell
{
	public CFixtureCell(CFixture fxt)
	{
		this.setFixture(fxt);
		
		if (fxt == null)
		{
			this.setIsDummy(true);
		}
	}
	
	@Override
	public String toString()
	{
		return this.isDummy() ? "N/A" : this.getFixture().toString();
	}
	
}
