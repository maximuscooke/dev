package com.maximuscooke.lib.common.gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class CJDialog extends JDialog
{
	public CJDialog()
	{
	}

	public CJDialog(Frame owner)
	{
		super(owner);
	}

	public CJDialog(Dialog owner)
	{
		super(owner);
	}

	public CJDialog(Window owner)
	{
		super(owner);
	}

	public CJDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
	}

	public CJDialog(Frame owner, String title)
	{
		super(owner, title);
	}

	public CJDialog(Dialog owner, boolean modal)
	{
		super(owner, modal);
	}

	public CJDialog(Dialog owner, String title)
	{
		super(owner, title);
	}

	public CJDialog(Window owner, ModalityType modalityType)
	{
		super(owner, modalityType);
	}

	public CJDialog(Window owner, String title)
	{
		super(owner, title);
	}

	public CJDialog(Frame owner, String title, boolean modal)
	{
		super(owner, title, modal);
	}

	public CJDialog(Dialog owner, String title, boolean modal)
	{
		super(owner, title, modal);
	}

	public CJDialog(Window owner, String title, ModalityType modalityType)
	{
		super(owner, title, modalityType);
	}

	public CJDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc)
	{
		super(owner, title, modal, gc);
	}

	public CJDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc)
	{
		super(owner, title, modal, gc);
	}

	public CJDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc)
	{
		super(owner, title, modalityType, gc);
	}
}
