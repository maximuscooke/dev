package com.maximuscooke.app.soccerboss;

import java.util.List;

import javax.swing.ImageIcon;

import com.maximuscooke.lib.common.CApplication;
import com.maximuscooke.lib.common.CResource;
import com.maximuscooke.lib.common.IExportObject;
import com.maximuscooke.lib.common.IMutableIconTreeNode;

public abstract class CGameObject extends CUniqueObject implements IMutableIconTreeNode, IExportObject
{
	private static final long serialVersionUID = 8208333601622806241L;
	
	private String mImageName16=null;
	private ImageIcon mImageIcon16=null;

	public CGameObject()
	{
	}
	
	public CGameObject(String name)
	{
		super(name);
	}

	public CGameObject(String name, int id)
	{
		super(name, id);
	}

	public static String exportString(String str)
	{
		return (str == null) ? CApplication.NULL_PTR_STRING : str;
	}
	
	public static String importString(String str)
	{
		return (str != null && str.equalsIgnoreCase(CApplication.NULL_PTR_STRING)) ? null : str;
	}

	public final String getImageName16()
	{
		return this.mImageName16;
	}

	public final void setImageName16(String fp)
	{
		this.mImageName16 = fp;
		
		this.mImageIcon16 = null; // force a reload
	}
	
	@Override
	public ImageIcon getSelectedIcon()
	{
		return this.getDefaultIcon();
	}

	@Override
	public ImageIcon getLeafIcon()
	{
		return this.getDefaultIcon();
	}

	@Override
	public ImageIcon getExpandedIcon()
	{
		return this.getDefaultIcon();
	}

	@Override
	public ImageIcon getDefaultIcon()
	{
		if (this.mImageIcon16 == null)
		{
			this.mImageIcon16 = CResource.getResourceAsImageIcon(CSoccerBoss.RES_DIR + this.mImageName16);
		}
		
		return this.mImageIcon16;
	}

	@Override
	public String exportObject(String separator)
	{
		return null;
	}

	@Override
	public void importObject(List<String> values)
	{
	}
}
