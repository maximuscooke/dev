package com.maximuscooke.lib.common.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class CJDialogEx extends CJDialog
{
	private CJPanel mInfoPanel = null;
	private ImageIcon mImageIcon = null;
	private CJLabel mIconLbl = null;
	private CJLabel mTitleLbl = null;
	private CJLabel mDescLbl = null;

	private static Font mDescFont = new Font("Serif", Font.PLAIN, 13);
	private static Font mTitleTextFont = new Font("Serif", Font.BOLD, 15);

	public CJDialogEx(Frame owner, String title, String titleText, String description, int width, int height, ImageIcon image, boolean modal)
	{
		super(owner, title, modal);

		mInfoPanel = new CJPanel();

		mInfoPanel.setLayout(null);

		int preferredHeight = 64;

		int rightEdge = width - 16;

		int leftEdge = 8;

		if (image != null)
		{
			preferredHeight = image.getIconHeight() + 16;

			mIconLbl = new CJLabel(image);

			mIconLbl.setBounds((width - image.getIconWidth() - 16), 8, image.getIconWidth(), image.getIconHeight());

			mInfoPanel.add(mIconLbl);

			rightEdge = rightEdge - image.getIconWidth() - 16;
		}

		if (titleText != null)
		{
			mTitleLbl = new CJLabel(titleText);

			mTitleLbl.setFont(mTitleTextFont);

			mTitleLbl.setBounds(leftEdge, 8, rightEdge, 20);

			mInfoPanel.add(mTitleLbl);
		}

		if (description != null)
		{
			mDescLbl = new CJLabel(description);

			mDescLbl.setFont(mDescFont);

			mDescLbl.setBounds(leftEdge + 12, 28, (rightEdge - 12), 20);

			mDescLbl.setForeground(Color.GRAY);

			mInfoPanel.add(mDescLbl);
		}

		mInfoPanel.setPreferredSize(100, preferredHeight);

		mInfoPanel.setBackground(Color.WHITE);

		this.add(this.mInfoPanel, BorderLayout.NORTH);

		this.setSize(width, height);

		this.setLocationRelativeTo(owner);

		this.setResizable(false);
	}

	public final Color getInfoPanelColor()
	{
		return mInfoPanel.getBackground();
	}

	public final void setInfoPanelColor(Color c)
	{
		this.mInfoPanel.setBackground(c);
	}

	public final ImageIcon getInfoPanelImageIcon()
	{
		return this.mImageIcon;
	}

}
