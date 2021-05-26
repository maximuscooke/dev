package com.maximuscooke.lib.common.lambda;

import com.maximuscooke.lib.common.gui.CJFileChooser;

@FunctionalInterface
public interface IFileChooser
{
	int run(CJFileChooser f);
}
