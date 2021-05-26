package com.maximuscooke.lib.common.gui;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class CJComboBox<T> extends JComboBox<T>
{
	public CJComboBox()
	{
	}

	public CJComboBox(ComboBoxModel<T> aModel)
	{
		super(aModel);
	}

	public CJComboBox(T[] items)
	{
		super(items);
	}

	public CJComboBox(Vector<T> items)
	{
		super(items);
	}

}
