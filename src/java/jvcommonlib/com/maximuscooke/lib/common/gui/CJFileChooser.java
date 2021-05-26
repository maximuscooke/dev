package com.maximuscooke.lib.common.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.maximuscooke.lib.common.lambda.IFileChooser;

@SuppressWarnings("serial")
public class CJFileChooser extends JFileChooser
{
	public CJFileChooser()
	{
	}

	public CJFileChooser(String currentDirectoryPath)
	{
		super(currentDirectoryPath);
	}

	public CJFileChooser(File currentDirectory)
	{
		super(currentDirectory);
	}

	public CJFileChooser(FileSystemView fsv)
	{
		super(fsv);
	}

	public CJFileChooser(File currentDirectory, FileSystemView fsv)
	{
		super(currentDirectory, fsv);
	}

	public CJFileChooser(String currentDirectoryPath, FileSystemView fsv)
	{
		super(currentDirectoryPath, fsv);
	}
	
	public static CJFileChooser runFileChooser(IFileChooser fnc, String title, int selectionMode)
	{
		return runFileChooser(fnc, title, selectionMode, ".");
	}
	
	public static CJFileChooser runFileChooser(IFileChooser fnc, String title, int selectionMode, String defaultDir)
	{
		return runFileChooser(fnc, title, null, selectionMode, defaultDir, false);
	}
		
	public static CJFileChooser runFileChooser(IFileChooser fnc, String title, Component parent, int selectionMode, String defaultDir, boolean bEnableMultiSection)
	{
		CJFileChooser fc = new CJFileChooser(defaultDir);
		
		fc.setDialogTitle(title);
		
		fc.setFileSelectionMode(selectionMode);
		
		fc.setMultiSelectionEnabled(bEnableMultiSection);
		
		if (fnc != null)
		{
			fnc.run(fc);
		}
				
		return fc;
	}
}
