package com.maximuscooke.lib.common.gui;

import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class CJInternalFrame extends JInternalFrame
{
	public CJInternalFrame()
	{
	}

	public CJInternalFrame(String title)
	{
		super(title);
	}

	public CJInternalFrame(String title, boolean resizable)
	{
		super(title, resizable);
	}

	public CJInternalFrame(String title, boolean resizable, boolean closable)
	{
		super(title, resizable, closable);
	}

	public CJInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable)
	{
		super(title, resizable, closable, maximizable);
	}

	public CJInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
	{
		super(title, resizable, closable, maximizable, iconifiable);
	}

}
