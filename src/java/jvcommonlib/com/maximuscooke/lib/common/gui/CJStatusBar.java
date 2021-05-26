package com.maximuscooke.lib.common.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class CJStatusBar extends CJPanel
{
	private CJLabel mLabel = new CJLabel("Ready.");
	
	public CJStatusBar()
	{
		this(100, 28);
	}

	public CJStatusBar(int width, int height)
	{
		super(true);
		
		this.setLayout( new FlowLayout(FlowLayout.LEFT) );

		this.mLabel.setPreferredSize(new Dimension(width, height));

		this.setStatusBarHorizontalAlignment(CJLabel.LEFT);

		this.add(mLabel);
	}

	public void setStatusBarText(String text)
	{
		this.mLabel.setText(text);
	}

	public void setStatusBarHorizontalAlignment(int alignment)
	{
		this.mLabel.setHorizontalAlignment(alignment);
	}

	public void setStatusBarColor(Color c)
	{
		this.setBackground(c);

		this.mLabel.setBackground(c);
	}
}
