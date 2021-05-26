package com.maximuscooke.lib.common.gui;

import java.awt.Component;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CJScrollPane extends JScrollPane
{
	public CJScrollPane()
	{
	}

	public CJScrollPane(Component view)
	{
		super(view);
	}

	public CJScrollPane(int vsbPolicy, int hsbPolicy)
	{
		super(vsbPolicy, hsbPolicy);
	}

	public CJScrollPane(Component view, int vsbPolicy, int hsbPolicy)
	{
		super(view, vsbPolicy, hsbPolicy);
	}
}
