package com.maximuscooke.app.soccerboss.editor.db;

import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.app.soccerboss.CRegion;

@SuppressWarnings("serial")
public class CTabPgRegion extends CTabPgGameObject
{	
	private CRegion mRegion=null;
		
	public CTabPgRegion()
	{
		super("Regions");
	}
	
	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{
		this.setIsInitializing(true);
		
		boolean isRegion = (gmobj instanceof CRegion);

		this.initGameObjectTabPg(db, gmobj, isRegion);
								
		this.setIsInitializing(false);
	}
	
	@Override
	protected void onUpdate()
	{
		super.onUpdate();
		
//		try
//		{
//			CEditor.getInstance().getDB().updateRegion(this.mRegion, this.getNameTextField().getText(), (String)this.getImageComboBox().getSelectedItem());
//		} 
//		catch (Exception e)
//		{
//			JOptionPane.showMessageDialog(this, e.getMessage(), "Error Adding Updating Region", JOptionPane.ERROR_MESSAGE);
//		}
	}
	
	@Override
	protected void onAdd()
	{
		super.onAdd();
				
//		try
//		{
//			CEditor.getInstance().getDB().addRegion( new CRegion(this.getNameTextField().getText(), -1, (String)this.getImageComboBox().getSelectedItem()) );
//		} 
//		catch (CDuplicateKeyException e)
//		{
//			JOptionPane.showMessageDialog(this, e.getMessage(), "Error Adding Region", JOptionPane.ERROR_MESSAGE);
//		}
	}
	
	@Override
	protected void onRemove()
	{
		super.onRemove();
		
		CEditor.getInstance().getDB().deleteRegion(this.mRegion);
	}
	
	@Override
	protected void onContentChanged()
	{
	}
}
