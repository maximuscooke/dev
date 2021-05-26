package com.maximuscooke.app.soccerboss.editor.schedule;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;

import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJOptionDialog;

@SuppressWarnings("serial")
public abstract class CDlgBase extends CJOptionDialog
{
	protected Font mDefaultFont = new Font("Helvetica", Font.PLAIN, 14);

	public CDlgBase(String title, String titleText, String description, int width, int height, ImageIcon image, boolean modal)
	{
		super(CScheduleEditor.getInstance(), title, titleText, description, width, height, image, modal);
	}

	protected CJLabel createLabel(String txt, Dimension d)
	{
		CJLabel lbl = new CJLabel(txt);
		
		lbl.setPreferredSize(d);
		
		lbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		lbl.setFont(this.mDefaultFont);
		
		return lbl;
	}


}
