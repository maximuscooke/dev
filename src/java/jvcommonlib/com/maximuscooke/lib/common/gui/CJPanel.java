package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CJPanel extends JPanel
{
	public CJPanel()
	{
	}

	public CJPanel(LayoutManager layout)
	{
		super(layout);
	}

	public CJPanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
	}

	public CJPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
	}

	public final void setPreferredSize(int w, int h)
	{
		this.setPreferredSize(new Dimension(w, h));
	}
}
