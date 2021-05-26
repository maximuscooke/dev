package com.maximuscooke.app.soccerboss.editor.db;

import java.awt.Font;

import javax.swing.ImageIcon;

import com.maximuscooke.lib.common.gui.CJOptionDialog;

@SuppressWarnings("serial")
public abstract class CDlgBase extends CJOptionDialog
{
	protected Font mDefaultFont = new Font("Helvetica", Font.PLAIN, 14);

	public CDlgBase(String title, String titleText, String description, int width, int height, ImageIcon image, boolean modal)
	{
		super(CEditor.getInstance(), title, titleText, description, width, height, image, modal);
	}


}
