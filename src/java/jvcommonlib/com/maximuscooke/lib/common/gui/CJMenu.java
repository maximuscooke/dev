package com.maximuscooke.lib.common.gui;

import javax.swing.Action;
import javax.swing.JMenu;

@SuppressWarnings("serial")
public class CJMenu extends JMenu
{
	public CJMenu()
	{
	}

	public CJMenu(String s)
	{
		super(s);
	}

	public CJMenu(Action a)
	{
		super(a);
	}

	public CJMenu(String s, boolean b)
	{
		super(s, b);
	}

}
