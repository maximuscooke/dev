package com.maximuscooke.lib.common.gui;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

@SuppressWarnings("serial")
public class CJList<T> extends JList<T>
{
	public CJList()
	{
	}

	public CJList(ListModel<T> dataModel)
	{
		super(dataModel);
	}

	public CJList(T[] listData)
	{
		super(listData);
	}

	public CJList(Vector<T> listData)
	{
		super(listData);
	}

}
