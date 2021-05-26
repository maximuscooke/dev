package com.maximuscooke.lib.common.gui;

import javax.swing.SpinnerListModel;

@SuppressWarnings("serial")
public class CJSpinnerList<T> extends CJSpinnerEx
{
	public CJSpinnerList(T[] list)
	{
		this(new SpinnerListModel(list));
	}

	public CJSpinnerList(SpinnerListModel model)
	{
		super(model);
	}

}
