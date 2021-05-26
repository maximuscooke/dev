package com.maximuscooke.app.soccerboss.editor.db;

import javax.swing.DefaultComboBoxModel;

import com.maximuscooke.app.soccerboss.CClub;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;
import com.maximuscooke.lib.common.gui.CJTextField;

@SuppressWarnings("serial")
public class CTabPgClub extends CTabPgGameObject
{
	private DefaultComboBoxModel<RegionComboBoxItem> mRegionModel = new DefaultComboBoxModel<RegionComboBoxItem>();

	private CJComboBox<RegionComboBoxItem> mRegionCB = new CJComboBox<RegionComboBoxItem>(mRegionModel);	

	private CJLabel mRegionLbl = new CJLabel("Region : ");
	private CJLabel mNickNameLbl = new CJLabel("Nick Name : ");
	private CJLabel mStadiumNameLbl = new CJLabel("Stadium : ");
	private CJLabel mStadiumCapacityLbl = new CJLabel("Stadium Capacity :");
	
	private CJTextField mNickNameTF = new CJTextField();
	private CJTextField mStadiumNameTF = new CJTextField();
	
	private CJSpinnerNumeric mStadiumCapacity = new CJSpinnerNumeric(40, 2, 1000, 1);
	
	private CClub mClub=null;

	public CTabPgClub()
	{
		super("Clubs");
		
		int rowHeight = 28;
		
		this.add(this.mRegionLbl);
		
		this.add(this.mRegionCB);
		
		this.add(this.mNickNameLbl);
		
		this.add(this.mNickNameTF);
		
		this.add(this.mStadiumNameLbl);
		
		this.add(this.mStadiumCapacityLbl);
		
		this.add(this.mStadiumCapacity);
		
		this.add(this.mStadiumNameTF);
		
		this.add(this.mStadiumCapacity);
		
		this.mRegionLbl.setBounds(8, 110, 150, rowHeight);
		
		this.mRegionLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mRegionCB.setBounds(158, 110, 250, rowHeight);
		
		this.mNickNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mNickNameLbl.setBounds(8, 146, 150, rowHeight);
		
		this.mNickNameTF.setBounds(158, 146, 250, rowHeight);
		
		this.mNickNameTF.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mStadiumNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mStadiumNameLbl.setBounds(8, 178, 150, rowHeight);
		
		this.mStadiumNameTF.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mStadiumNameTF.setBounds(158, 178, 250, rowHeight);
		
		this.mStadiumCapacityLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mStadiumCapacityLbl.setBounds(8, 210, 150, rowHeight);
		
		this.mStadiumCapacity.setBounds(158, 210, 250, rowHeight);
		
		this.mStadiumCapacity.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mRegionLbl.setEnabled(false);
		
		this.mRegionCB.setEnabled(false);
		
		this.mNickNameLbl.setEnabled(false);
		
		this.mNickNameTF.setEnabled(false);
		
		this.mStadiumNameTF.setEnabled(false);
		
		this.mStadiumCapacityLbl.setEnabled(false);
		
		this.mStadiumNameLbl.setEnabled(false);
		
		this.mStadiumCapacity.setEnabled(false);
	}
	
	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{
		this.setIsInitializing(true);
		
		boolean isClub = (gmobj instanceof CClub);
		
		this.mClub = (CClub)gmobj;

		this.initGameObjectTabPg(db, gmobj, isClub);
		
		this.mRegionLbl.setEnabled(isClub);
		
		this.mRegionCB.setEnabled(isClub);
		
		this.mNickNameLbl.setEnabled(isClub);
		
		this.mNickNameTF.setEnabled(isClub);
		
		this.mStadiumNameTF.setEnabled(isClub);
		
		this.mStadiumCapacityLbl.setEnabled(isClub);
		
		this.mStadiumNameLbl.setEnabled(isClub);
		
		this.mStadiumCapacity.setEnabled(isClub);
								
		this.setIsInitializing(false);
	}

	@Override
	protected void onContentChanged()
	{
	}
}
