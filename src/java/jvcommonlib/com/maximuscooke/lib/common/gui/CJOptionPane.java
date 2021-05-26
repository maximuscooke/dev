package com.maximuscooke.lib.common.gui;

import javax.swing.Icon;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CJOptionPane extends JOptionPane
{
	public CJOptionPane()
	{
	}

	public CJOptionPane(Object message)
	{
		super(message);
	}

	public CJOptionPane(Object message, int messageType)
	{
		super(message, messageType);
	}

	public CJOptionPane(Object message, int messageType, int optionType)
	{
		super(message, messageType, optionType);
	}

	public CJOptionPane(Object message, int messageType, int optionType, Icon icon)
	{
		super(message, messageType, optionType, icon);
	}

	public CJOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options)
	{
		super(message, messageType, optionType, icon, options);
	}

	public CJOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options, Object initialValue)
	{
		super(message, messageType, optionType, icon, options, initialValue);
	}

}
