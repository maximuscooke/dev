package com.maximuscooke.lib.common.gui;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class CJTree extends JTree
{
	public CJTree()
	{
	}

	public CJTree(Object[] value)
	{
		super(value);
	}

	public CJTree(Vector<?> value)
	{
		super(value);
	}

	public CJTree(Hashtable<?, ?> value)
	{
		super(value);
	}

	public CJTree(TreeNode root)
	{
		super(root);
	}

	public CJTree(TreeModel newModel)
	{
		super(newModel);
	}

	public CJTree(TreeNode root, boolean asksAllowsChildren)
	{
		super(root, asksAllowsChildren);
	}

}
