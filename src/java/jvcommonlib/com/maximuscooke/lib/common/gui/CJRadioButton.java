package com.maximuscooke.lib.common.gui;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class CJRadioButton extends JRadioButton
{
	public CJRadioButton()
	{
	}

	public CJRadioButton(Icon icon)
	{
		super(icon);
	}

	public CJRadioButton(Action a)
	{
		super(a);
	}

	public CJRadioButton(String text)
	{
		super(text);
	}

	public CJRadioButton(Icon icon, boolean selected)
	{
		super(icon, selected);
	}

	public CJRadioButton(String text, boolean selected)
	{
		super(text, selected);
	}

	public CJRadioButton(String text, Icon icon)
	{
		super(text, icon);
	}

	public CJRadioButton(String text, Icon icon, boolean selected)
	{
		super(text, icon, selected);
	}

	public static ButtonGroup addToGroup(CJRadioButton... btns)
	{
		ButtonGroup g = new ButtonGroup();

		if (btns != null && btns.length > 0)
		{
			for (CJRadioButton btn : btns)
			{
				g.add(btn);
			}

			btns[0].setSelected(true);
		}

		return g;
	}
}
