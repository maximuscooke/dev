package com.maximuscooke.lib.common.gui;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class CJProgressBar extends JProgressBar
{
	public CJProgressBar()
	{
	}

	public CJProgressBar(int orient)
	{
		super(orient);
	}

	public CJProgressBar(BoundedRangeModel newModel)
	{
		super(newModel);
	}

	public CJProgressBar(int min, int max)
	{
		super(min, max);
	}

	public CJProgressBar(int orient, int min, int max)
	{
		super(orient, min, max);
	}
}
