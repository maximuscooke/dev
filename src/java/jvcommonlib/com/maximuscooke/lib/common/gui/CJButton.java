package com.maximuscooke.lib.common.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class CJButton extends JButton
{
	public CJButton()
	{
	}

	public CJButton(Icon icon)
	{
		super(icon);
	}

	public CJButton(String text)
	{
		super(text);
	}

	public CJButton(Action a)
	{
		super(a);
	}

	public CJButton(String text, Icon icon)
	{
		super(text, icon);
	}

	public static CJButton createButton(String title, Icon icon, int x, int y, int width, int height, boolean bEnable)
	{
		CJButton newBtn = new CJButton(title, icon);

		newBtn.setBounds(x, y, width, height);

		newBtn.setEnabled(bEnable);

		return newBtn;
	}
}
