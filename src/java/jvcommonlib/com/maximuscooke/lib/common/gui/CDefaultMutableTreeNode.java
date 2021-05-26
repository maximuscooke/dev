package com.maximuscooke.lib.common.gui;

import java.util.Collections;
import java.util.Comparator;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class CDefaultMutableTreeNode extends DefaultMutableTreeNode
{
	private int mType = 0;
	
	public CDefaultMutableTreeNode()
	{
	}
	
	public CDefaultMutableTreeNode(Object userObject, int type)
	{
		super(userObject);
		
		this.setType(type);
	}

	public CDefaultMutableTreeNode(Object userObject)
	{
		this(userObject, 0);
	}

	public CDefaultMutableTreeNode(Object userObject, boolean allowsChildren)
	{
		super(userObject, allowsChildren);
	}

	@SuppressWarnings("unchecked")
	public void sort(Comparator<? extends CDefaultMutableTreeNode> c)
	{
		Collections.sort(children, c);
	}

	public final int getType()
	{
		return mType;
	}

	public final void setType(int t)
	{
		this.mType = t;
	}
}
