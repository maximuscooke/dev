package com.maximuscooke.app.soccerboss.editor.utilities;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.maximuscooke.lib.common.gui.CJFrameEx;
import com.maximuscooke.lib.common.gui.CJTabbedPaneEx;

@SuppressWarnings("serial")
public class CUtils extends CJFrameEx
{
	private CJTabbedPaneEx mTabPane = new CJTabbedPaneEx();
	
	private CSetupFilePg mSetupFilePg = new CSetupFilePg();

	public CUtils()
	{
		super("Soccer Boss Utilities", 800, 600, null, JFrame.EXIT_ON_CLOSE);
		
		this.add(this.mTabPane, BorderLayout.CENTER);
		
		this.mTabPane.addPage(this.mSetupFilePg);
				
		this.setVisible(true);
	}

	public static void main(String[] args)
	{		
		javax.swing.SwingUtilities.invokeLater( () -> new CUtils() );
	}
}
