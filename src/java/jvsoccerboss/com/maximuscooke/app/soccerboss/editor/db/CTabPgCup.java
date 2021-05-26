package com.maximuscooke.app.soccerboss.editor.db;

import com.maximuscooke.app.soccerboss.CCup;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;

@SuppressWarnings("serial")
public class CTabPgCup extends CTabPgGameObject
{
	private CJLabel mClubCountLbl = new CJLabel("Club Count :");
	
	private CJSpinnerNumeric mClubCount = new CJSpinnerNumeric(100, 4, 300, 1);

	public CTabPgCup()
	{
		super("Cups");
		
		int rowHeight = 28;
		
		this.add(this.mClubCountLbl);
		
		this.add(this.mClubCount);
		
		this.mClubCountLbl.setBounds(8, 110, 150, rowHeight);
				
		this.mClubCountLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mClubCount.setBounds(158, 110, 100, rowHeight);
		
		this.mClubCount.setHorizontalAlignment(CJLabel.CENTER);

		this.mClubCountLbl.setEnabled(false);
		
		this.mClubCount.setEnabled(false);
	}

	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{
		this.setIsInitializing(true);
		
		boolean isCup = (gmobj instanceof CCup);

		this.initGameObjectTabPg(db, gmobj, isCup);
		
		this.mClubCountLbl.setEnabled(isCup);
		
		this.mClubCount.setEnabled(isCup);
				
		if (isCup)
		{
			CCup cup = (CCup)gmobj;
			
			this.mClubCount.setValue( (Integer)cup.getClubCount() );
			
		}
										
		this.setIsInitializing(false);
	}

	@Override
	protected void onContentChanged()
	{

	}

}
