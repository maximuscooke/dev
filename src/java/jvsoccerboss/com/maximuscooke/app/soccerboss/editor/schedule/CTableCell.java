	package com.maximuscooke.app.soccerboss.editor.schedule;

import com.maximuscooke.app.soccerboss.CFixture;
import com.maximuscooke.lib.common.CApplication;

public class CTableCell
{
	public static final byte IS_DUMMY_BIT = 0;
	public static final byte IS_DATE_ONLY_BIT = 0;

	private CFixture mFixture=null;
	
	private int mOptions=0;

	public CTableCell()
	{
	}
	
	public final boolean isScheduled()
	{
		return (this.mFixture == null) ? false : this.getFixture().isScheduled();
	}
	
	public final CFixture getFixture()
	{
		return mFixture;
	}

	public final void setFixture(CFixture f)
	{
		this.mFixture = f;
	}

	public final int getOptions()
	{
		return mOptions;
	}

	public final void setOptions(int opt)
	{
		this.mOptions = opt;
	}
	
	public final boolean isDateOnly()
	{
		return CApplication.isBitSet(CTableCell.IS_DATE_ONLY_BIT, this.mOptions);		
	}
	
	public final void setIsDateOnly(boolean bEnable)
	{
		this.mOptions = bEnable ? CApplication.setBit(CTableCell.IS_DATE_ONLY_BIT, this.mOptions) : CApplication.clearBit(CTableCell.IS_DATE_ONLY_BIT, this.mOptions);
	}
	
	public final boolean isDummy()
	{
		return CApplication.isBitSet(CTableCell.IS_DUMMY_BIT, this.mOptions);		
	}
	
	public final void setIsDummy(boolean bEnable)
	{
		this.mOptions = bEnable ? CApplication.setBit(CTableCell.IS_DUMMY_BIT, this.mOptions) : CApplication.clearBit(CTableCell.IS_DUMMY_BIT, this.mOptions);
	}

}
