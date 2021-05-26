package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CJLabel extends JLabel
{
	public CJLabel()
	{
	}

	public CJLabel(String text)
	{
		super(text);
	}

	public CJLabel(Icon image)
	{
		super(image);
	}

	public CJLabel(String text, int horizontalAlignment)
	{
		super(text, horizontalAlignment);
	}

	public CJLabel(Icon image, int horizontalAlignment)
	{
		super(image, horizontalAlignment);
	}

	public CJLabel(String text, Icon icon, int horizontalAlignment)
	{
		super(text, icon, horizontalAlignment);
	}

	public final void setPreferredSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public static CJLabel createLabel(String text, int x, int y, int width, int height)
	{
		return createLabel(text, x, y, width, height, CJLabel.CENTER);
	}
	
	public static CJLabel createLabel(String text, int x, int y, int width, int height, int horizontalAlignment)
	{
		return createLabel(text, x, y, width, height, horizontalAlignment, true);
	}

	public static CJLabel createLabel(String text, int x, int y, int width, int height, int horizontalAlignment, boolean bEnabled)
	{
		CJLabel lbl = new CJLabel(text);

		lbl.setBounds(x, y, width, height);

		lbl.setHorizontalAlignment(horizontalAlignment);

		lbl.setEnabled(bEnabled);

		return lbl;
	}
}
