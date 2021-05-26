package com.maximuscooke.lib.common.gui;

@SuppressWarnings("serial")
public class CJTabbedPaneEx extends CJTabbedPane
{
	public CJTabbedPaneEx()
	{
	}

	public CJTabbedPaneEx(int tabPlacement)
	{
		super(tabPlacement);
	}

	public CJTabbedPaneEx(int tabPlacement, int tabLayoutPolicy)
	{
		super(tabPlacement, tabLayoutPolicy);
	}

	public final CJTabPage getActivePage()
	{
		return (CJTabPage) this.getSelectedComponent();
	}
	
	public void addPage(CJTabPage pg)
	{
		this.add(pg.getTitle(), pg);
	}
	
	public void removePage()
	{
		this.remove( this.getActivePage() );
	}
	
	public void removePage(CJTabPage pg)
	{
		this.remove(pg);
	}
	
	public final int getPageCount()
	{
		return this.getTabCount();
	}
	
	public final CJTabPage getPageAt(int index)
	{
		return (CJTabPage)this.getComponentAt(index);
	}
}
