package com.maximuscooke.lib.common.gui;

import java.awt.Component;

import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class CJSplitPane extends JSplitPane
{
	public CJSplitPane()
	{
	}

	public CJSplitPane(int newOrientation)
	{
		super(newOrientation);
	}

	public CJSplitPane(int newOrientation, boolean newContinuousLayout)
	{
		super(newOrientation, newContinuousLayout);
	}

	public CJSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newLeftComponent, newRightComponent);
	}

	public CJSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
	}

	public void setSplitPercentage(double percentage)
	{
		this.setDividerLocation(percentage);

		this.setResizeWeight(percentage);
	}
}
