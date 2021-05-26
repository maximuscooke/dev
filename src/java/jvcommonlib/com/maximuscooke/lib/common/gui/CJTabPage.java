package com.maximuscooke.lib.common.gui;

@SuppressWarnings("serial")
public class CJTabPage extends CJPanel
{
	private String mTitle = null;

	public CJTabPage(String title)
	{
		super(true);

		this.mTitle = title;
	}

	public final String getTitle()
	{
		return this.mTitle;
	}
}
