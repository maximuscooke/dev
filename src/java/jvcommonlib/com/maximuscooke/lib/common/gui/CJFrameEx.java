package com.maximuscooke.lib.common.gui;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CJFrameEx extends CJFrame implements WindowListener
{
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;

	public CJFrameEx(String title)
	{
		this(title, 800, 600, CJFrameEx.DISPOSE_ON_CLOSE);
	}

	public CJFrameEx(String title, int closeOperation)
	{
		this(title, 800, 600, closeOperation);
	}

	public CJFrameEx(String title, int width, int height, int closeOperation)
	{
		this(title, width, height, null, closeOperation);
	}

	public CJFrameEx(String title, int width, int height, Component locationRelativeTo, int closeOperation)
	{
		super(title);

		this.setDefaultCloseOperation(closeOperation);

		this.setSize(width, height);

		this.setLocationRelativeTo(locationRelativeTo);

		this.addWindowListener(this);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
}
