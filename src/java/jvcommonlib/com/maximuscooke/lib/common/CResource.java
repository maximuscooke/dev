package com.maximuscooke.lib.common;

import java.awt.Toolkit;
import java.applet.Applet;
import java.applet.AudioClip;
import javax.swing.ImageIcon;

public abstract class CResource
{
	public static ImageIcon getResourceAsImageIcon(String filePath)
	{		
		return new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource(filePath));
	}

	public static AudioClip getResourceAsAudioClip(String url) throws Exception
	{
		return Applet.newAudioClip(Toolkit.getDefaultToolkit().getClass().getResource(url));
	}
	
	public static ImageIcon getImageIcon(String fPath)
	{
		return new ImageIcon(fPath);
	}
}
