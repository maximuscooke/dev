package com.maximuscooke.lib.common.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public abstract class CJOptionDialog extends CJDialog
{
	private CJPanel mNorthPanel = new CJPanel();
	private CJPanel mSouthPanel = new CJPanel();
	private CJPanel mCenterPanel = new CJPanel();
	private CJPanel mTextPanel = new CJPanel();
	
	private CJLabel mIconLbl = null;
	private CJLabel mTitleLbl = null;
	private CJLabel mDescLbl = null;
	
	private CJButton mOkBtn = new CJButton("OK");
	private CJButton mCancelBtn = new CJButton("Cancel");
	
	private static Font mDescFont = new Font("Helvetica", Font.PLAIN, 14);
	private static Font mTitleTextFont = new Font("Helvetica", Font.BOLD, 18);
	
	public CJOptionDialog(Frame owner, String title, String titleText, String description)
	{
		this( owner,  title,  titleText,  description, 800, 600, null, true);
	}
	
	public CJOptionDialog(Frame owner, String title, String titleText, String description, int width, int height)
	{
		this( owner,  title,  titleText,  description,  width,  height, null, true);
	}
	
	public CJOptionDialog(Frame owner, String title, String titleText, String description, int width, int height, boolean modal)
	{
		this( owner,  title,  titleText,  description,  width,  height, null, modal);
	}
	
	public CJOptionDialog(Frame owner, String title, String titleText, String description, int width, int height, ImageIcon image, boolean modal)
	{
		super(owner, title, modal);
		
		Dimension btnSize = new Dimension(100, 22);
		
		this.mCancelBtn.setPreferredSize( btnSize);
		
		this.mOkBtn.setPreferredSize( btnSize);
		
		this.mTextPanel.setLayout(null);
				
		this.mNorthPanel.setLayout( new BorderLayout() );
		
		this.mNorthPanel.setPreferredSize( new Dimension(width, 72) );
		
		this.mCenterPanel.setLayout(null);
		
		this.setDialogColor(Color.WHITE);
		
		this.mSouthPanel.add(this.mCancelBtn);

		this.mSouthPanel.add(this.mOkBtn);
		
		this.mSouthPanel.setPreferredSize( new Dimension(width, 32) );
		
		this.mSouthPanel.setLayout( new FlowLayout(FlowLayout.RIGHT) );
		
		this.add(this.mNorthPanel, BorderLayout.NORTH);
		
		this.add(this.mCenterPanel, BorderLayout.CENTER);
		
		this.add(this.mSouthPanel, BorderLayout.SOUTH);
		
		this.setSize( width, height );
		
		this.setLocationRelativeTo(owner);
		
		this.mCancelBtn.addActionListener( e -> onCancelClicked() );
		
		this.mOkBtn.addActionListener( e -> onOkClicked() );
		
		this.mNorthPanel.add(mTextPanel, BorderLayout.CENTER);
		
		this.mOkBtn.setEnabled(false);
		
		if (image != null)
		{
			this.mIconLbl = new CJLabel(image);
			
			int offset = 16;
			
			this.mIconLbl.setPreferredSize( image.getIconWidth() + offset, image.getIconHeight() + offset);
			
			this.mNorthPanel.add(this.mIconLbl, BorderLayout.EAST);
		}
		
		if (titleText != null)
		{
			this.mTitleLbl = new CJLabel(titleText);

			this.mTitleLbl.setFont(mTitleTextFont);

			this.mTitleLbl.setBounds(8, 8, width, 20);

			this.mTextPanel.add(mTitleLbl);
		}

		if (description != null)
		{
			this.mDescLbl = new CJLabel(description);

			this.mDescLbl.setFont(mDescFont);

			this.mDescLbl.setBounds(16, 28, width, 20);

			this.mDescLbl.setForeground(Color.GRAY);

			this.mTextPanel.add(mDescLbl);
		}
	}
	
	protected abstract void onOkClicked();
	
	protected void onCancelClicked()
	{
		this.dispose();
	}
	
	public void setDialogColor(Color c)
	{
		this.mNorthPanel.setBackground(c);
		
		this.mTextPanel.setBackground(c);
		
		this.mSouthPanel.setBackground(c);
	}

	public final CJPanel getNorthPanel()
	{
		return mNorthPanel;
	}

	public final CJPanel getSouthPanel()
	{
		return mSouthPanel;
	}

	public final CJPanel getCenterPanel()
	{
		return mCenterPanel;
	}
	
	public void setCancelButtonText(String txt)
	{
		this.mCancelBtn.setText(txt);
	}
	
	public void setOkButtonText(String txt)
	{
		this.mOkBtn.setText(txt);
	}

	public final CJButton getOkBtn()
	{
		return mOkBtn;
	}

	public final CJButton getCancelBtn()
	{
		return mCancelBtn;
	}
}
