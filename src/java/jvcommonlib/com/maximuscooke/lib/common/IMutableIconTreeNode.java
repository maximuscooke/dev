package com.maximuscooke.lib.common;

import javax.swing.ImageIcon;

public interface IMutableIconTreeNode
{
	ImageIcon getSelectedIcon();

	ImageIcon getLeafIcon();

	ImageIcon getExpandedIcon();

	ImageIcon getDefaultIcon();
}
