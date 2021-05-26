package com.maximuscooke.lib.common.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CJCheckBox extends JCheckBox
{
	public CJCheckBox()
	{
	}

	public CJCheckBox(Icon icon)
	{
		super(icon);
	}

	public CJCheckBox(String text)
	{
		super(text);
	}

	public CJCheckBox(Action a)
	{
		super(a);
	}

	public CJCheckBox(Icon icon, boolean selected)
	{
		super(icon, selected);
	}

	public CJCheckBox(String text, boolean selected)
	{
		super(text, selected);
	}

	public CJCheckBox(String text, Icon icon)
	{
		super(text, icon);
	}

	public CJCheckBox(String text, Icon icon, boolean selected)
	{
		super(text, icon, selected);
	}
	
	public final boolean getCheckedState()
	{
		return this.isSelected();
	}

}
