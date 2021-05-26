package com.maximuscooke.lib.common.gui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.Icon;

@SuppressWarnings("serial")
public class CJInfoBar extends CJPanel
{
	public static final int DEFAULT_BUTTON_HEIGHT = 74;
	public static final int DEFAULT_BUTTON_WIDTH = 72;

	private int mBtnWidth = DEFAULT_BUTTON_WIDTH;
	private int mBthHeight = DEFAULT_BUTTON_HEIGHT;

	{
		FlowLayout fl = (FlowLayout) this.getLayout();

		fl.setAlignment(FlowLayout.LEFT);
	}

	public CJInfoBar()
	{
		super(true);
	}

	public CJInfoBar(int buttonWidth, int buttonHeight)
	{
		super(true);

		this.setButtonWidth(buttonWidth);

		this.setButtonHeight(buttonHeight);
	}

	public CJInfoBar(LayoutManager layout)
	{
		super(layout);
	}

	public CJInfoBar(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
	}

	public CJInfoBar(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
	}

	public CJInfoButton addButton(String title, Icon icon)
	{
		return this.addButton(title, icon, getButtonWidth(), getButtonHeight());
	}

	public CJInfoButton addButton(String title, Icon icon, int width, int height)
	{
		CJInfoButton btn = new CJInfoButton(title, icon, width, height);

		this.add(btn);

		btn.setParentInfoBar(this);

		return btn;
	}

	public final int getButtonWidth()
	{
		return mBtnWidth;
	}

	public final void setButtonWidth(int btnWidth)
	{
		this.mBtnWidth = btnWidth;
	}

	public final int getButtonHeight()
	{
		return mBthHeight;
	}

	public final void setButtonHeight(int bthHeight)
	{
		this.mBthHeight = bthHeight;
	}

}
