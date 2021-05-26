package com.maximuscooke.lib.common.gui;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.ColorSelectionModel;

@SuppressWarnings("serial")
public class CJColorChooser extends JColorChooser
{
	public CJColorChooser()
	{
	}

	public CJColorChooser(Color initialColor)
	{
		super(initialColor);
	}

	public CJColorChooser(ColorSelectionModel model)
	{
		super(model);
	}
}
