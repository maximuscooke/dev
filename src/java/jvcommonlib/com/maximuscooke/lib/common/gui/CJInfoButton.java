package com.maximuscooke.lib.common.gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class CJInfoButton extends CJButton implements MouseListener
{
	private Border mEmptyBorder = BorderFactory.createEmptyBorder();
	private Border mRolloverBorder = BorderFactory.createRaisedSoftBevelBorder();

	private CJInfoBar mParentInfoBar = null;

	public CJInfoButton(String text, Icon icon, int width, int height)
	{
		super(text, icon);

		this.addMouseListener(this);

		this.setBorder(mEmptyBorder);

		this.setVerticalTextPosition(SwingConstants.BOTTOM);

		this.setHorizontalTextPosition(SwingConstants.CENTER);

		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		this.setBorder(mRolloverBorder);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		this.setBorder(mEmptyBorder);
	}

	public void setRolloverBorder(Border b)
	{
		this.mRolloverBorder = b;
	}

	public Border getRolloverBorder()
	{
		return this.mRolloverBorder;
	}

	public CJInfoBar getParentInfoBar()
	{
		return this.mParentInfoBar;
	}

	public void setParentInfoBar(CJInfoBar parent)
	{
		mParentInfoBar = parent;
	}

}
