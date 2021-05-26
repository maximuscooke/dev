package com.maximuscooke.app.soccerboss.editor.db;

import javax.swing.DefaultComboBoxModel;

import com.maximuscooke.app.soccerboss.CCountry;
import com.maximuscooke.app.soccerboss.CDatabase;
import com.maximuscooke.app.soccerboss.CGameObject;
import com.maximuscooke.app.soccerboss.CRegion;
import com.maximuscooke.app.soccerboss.CSoccerBoss;
import com.maximuscooke.lib.common.CCollections;
import com.maximuscooke.lib.common.collections.CArrayList;
import com.maximuscooke.lib.common.gui.CJComboBox;
import com.maximuscooke.lib.common.gui.CJLabel;
import com.maximuscooke.lib.common.gui.CJSpinnerNumeric;

@SuppressWarnings("serial")
public class CTabPgCountry extends CTabPgGameObject
{
	private DefaultComboBoxModel<RegionComboBoxItem> mRegionModel = new DefaultComboBoxModel<RegionComboBoxItem>();
	private DefaultComboBoxModel<String> mClubNameModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> mClubExtModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> mFirstNameModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> mSurnameModel = new DefaultComboBoxModel<String>();

	private CJComboBox<RegionComboBoxItem> mRegionCB = new CJComboBox<RegionComboBoxItem>(mRegionModel);	
	private CJComboBox<String> mClubNameCB = new CJComboBox<String>(mClubNameModel);	
	private CJComboBox<String> mClubExtCB = new CJComboBox<String>(mClubExtModel);	
	private CJComboBox<String> mFirstNameCB = new CJComboBox<String>(mFirstNameModel);
	private CJComboBox<String> mSurnameCB = new CJComboBox<String>(mSurnameModel);

	private CJSpinnerNumeric mMinClubs = new CJSpinnerNumeric(40, 2, 1000, 1);

	private CJLabel mRegionLbl = new CJLabel("Region :");
	private CJLabel mMinClubsLbl =  new CJLabel("Min Clubs :");
	private CJLabel mClubNameLbl = new CJLabel("Club Name File :");
	private CJLabel mClubExtLbl = new CJLabel("Club Ext File :");
	private CJLabel mFirstNameLbl = new CJLabel("First Name File :");
	private CJLabel mSurnameLbl = new CJLabel("Surname File :");
	
	private CCountry mCountry=null;

	public CTabPgCountry()
	{
		super("Countries");
		
		int rowHeight = 28;
		
		int col1  = 8;
		int col2  = col1+150;
			
		this.mMinClubsLbl.setBounds(col1, 146, 150, rowHeight);
		
		this.mMinClubsLbl.setHorizontalAlignment(CJLabel.RIGHT);
				
		this.mMinClubs.setHorizontalAlignment(CJLabel.CENTER);
		
		this.mMinClubs.setBounds(col2, 146, 250, rowHeight);
		
		this.mMinClubs.setEditable(false);
				
		this.mRegionLbl.setBounds(col1, 110, 150, rowHeight);
		
		this.mRegionLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mRegionCB.setBounds(col2, 110, 250, rowHeight);
		
		this.mClubNameLbl.setBounds(col1, 178, 150, rowHeight);
		
		this.mClubNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mClubNameCB.setBounds(col2, 178, 250, rowHeight);
		
		this.mClubExtLbl.setBounds(col1, 210, 150, rowHeight);
		
		this.mClubExtLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mClubExtCB.setBounds(col2, 210, 250, rowHeight);
					
		this.mFirstNameLbl.setBounds(col1, 242, 150, rowHeight);
		
		this.mFirstNameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mFirstNameCB.setBounds(col2, 242, 250, rowHeight);
		
		this.mSurnameLbl.setBounds(col1, 274, 150, rowHeight);
		
		this.mSurnameLbl.setHorizontalAlignment(CJLabel.RIGHT);
		
		this.mSurnameCB.setBounds(col2, 274, 250, rowHeight);

		this.add(this.mRegionLbl);
		
		this.add(this.mRegionCB);
				
		this.add(this.mMinClubsLbl);
		
		this.add(this.mMinClubs);
		
		this.add(this.mClubNameLbl);
		
		this.add(this.mClubNameCB);
		
		this.add(this.mFirstNameLbl);
		
		this.add(this.mFirstNameCB);
		
		this.add(this.mSurnameLbl);
		
		this.add(this.mSurnameCB);

		this.add(this.mClubExtLbl);
		
		this.add(this.mClubExtCB);
				
		this.mMinClubs.setEnabled(false);
		
		this.mMinClubsLbl.setEnabled(false);
		
		this.mRegionLbl.setEnabled(false);
		
		this.mRegionCB.setEnabled(false);
		
		this.mClubNameLbl.setEnabled(false);
		
		this.mClubNameCB.setEnabled(false);
		
		this.mClubExtLbl.setEnabled(false);
		
		this.mClubExtCB.setEnabled(false);

		this.mFirstNameLbl.setEnabled(false);
		
		this.mFirstNameCB.setEnabled(false);
		
		this.mSurnameLbl.setEnabled(false);
		
		this.mSurnameCB.setEnabled(false);
	}
			
	@Override
	public void initTabPage(CDatabase db, CGameObject gmobj, int options)
	{
		this.setIsInitializing(true);
		
		boolean isCountry = (gmobj instanceof CCountry);

		this.initGameObjectTabPg(db, gmobj, isCountry);
		
		this.mMinClubs.setEnabled(isCountry);
		
		this.mMinClubsLbl.setEnabled(isCountry);
		
		this.mRegionLbl.setEnabled(isCountry);
		
		this.mRegionCB.setEnabled(isCountry);
		
		this.mClubNameLbl.setEnabled(isCountry);
		
		this.mClubNameCB.setEnabled(isCountry);
		
		this.mClubExtLbl.setEnabled(isCountry);
		
		this.mClubExtCB.setEnabled(isCountry);

		this.mFirstNameLbl.setEnabled(isCountry);
		
		this.mFirstNameCB.setEnabled(isCountry);
		
		this.mSurnameLbl.setEnabled(isCountry);
		
		this.mSurnameCB.setEnabled(isCountry);
								
		this.setIsInitializing(false);

	}
	
	@Override
	protected void onRefresh()
	{
		super.onRefresh();
		
		this.mRegionModel.removeAllElements();
		
		this.mClubNameModel.removeAllElements();
		
		this.mClubExtModel.removeAllElements();
		
		this.mFirstNameModel.removeAllElements();
		
		this.mSurnameModel.removeAllElements();
		
		if (CEditor.getInstance() != null && CEditor.getInstance().getDB() != null)
		{
			CDatabase db = CEditor.getInstance().getDB();
			
			CArrayList<CRegion> list = new CArrayList<CRegion>(db.getRegionMap().values());
			
			CCollections.sortByName(list);

			for(CRegion rg : list)
			{
				this.mRegionModel.addElement( new RegionComboBoxItem(rg) );
			}
			
			CTabPgGameObject.fillComboBoxes(this.mFirstNameModel, CSoccerBoss.SETUP_DIR, CSoccerBoss.FIRSTNAME_EXT);
			
			CTabPgGameObject.fillComboBoxes(this.mSurnameModel, CSoccerBoss.SETUP_DIR, CSoccerBoss.SURNAME_EXT);
			
			CTabPgGameObject.fillComboBoxes(this.mClubNameModel, CSoccerBoss.SETUP_DIR, CSoccerBoss.CLUBNAME_EXT);
			
			CTabPgGameObject.fillComboBoxes(this.mClubExtModel, CSoccerBoss.SETUP_DIR, CSoccerBoss.CLUBEXT_EXT);
												
		}
	}
		
	@Override
	protected void onContentChanged()
	{
		if(!this.isInitializing())
		{			
			this.getRemoveBtn().setEnabled(true);
			
			this.getUpdateBtn().setEnabled(true);		
			
//			this.getAddBtn().setEnabled(this.getNameTextField().getText().length() > 0 && this.getImageComboBox().getSelectedIndex() >= 0);
		}		
	}

			
	@Override
	protected void onUpdate()
	{
		super.onUpdate();
	}		

	@Override
	protected void onAdd()
	{
		super.onAdd();
	}
	
	@Override
	protected void onRemove()
	{
		super.onRemove();
	}
}

class RegionComboBoxItem
{
	private CRegion mRegion;
	
	public RegionComboBoxItem(CRegion d)
	{
		this.mRegion = d;
	}
	
	@Override
	public String toString()
	{
		return this.mRegion.getName();
	}

	public final CRegion getRegion()
	{
		return mRegion;
	}
}

