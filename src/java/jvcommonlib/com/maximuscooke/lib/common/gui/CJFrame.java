package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CJFrame extends JFrame
{
	public CJFrame() throws HeadlessException
	{
	}

	public CJFrame(GraphicsConfiguration gc)
	{
		super(gc);
	}

	public CJFrame(String title) throws HeadlessException
	{
		super(title);
	}

	public CJFrame(String title, GraphicsConfiguration gc)
	{
		super(title, gc);
	}

	public void setMinimumSize(int width, int height)
	{
		this.setMinimumSize(new Dimension(width, height));
	}
}
